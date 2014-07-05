package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Cloudify {


    private static HashMap<String, Float> weights;
    static
    {
        weights = new HashMap<String, Float>();
        weights.put("sum", 1.0f);
        weights.put("count", 1.0f);
        weights.put("min", 1.0f);
        weights.put("max", 1.0f);
    }
    private static List<String> primitives = Arrays.asList("sum", "count", "min", "max");

    private static HashMap<String, ArrayList<String>> complexFunctions = new HashMap<String, ArrayList<String>>();
    private static HashMap<String, OutputFunction> udfs = new HashMap<String, OutputFunction>();

    public static CloudQuery toCloud(SQLQuery query) throws Exception {
        CloudQuery cloudQuery = new CloudQuery(query);

        cloudQuery.leafQuery.inputTables = cloudQuery.sqlQuery.inputTables;
        cloudQuery.leafQuery.joins = cloudQuery.sqlQuery.joins;
        cloudQuery.leafQuery.filters = cloudQuery.sqlQuery.filters;

        Cloudify.cloudifyGroupBy(cloudQuery);
        Cloudify.cloudifyOrderBy(cloudQuery);
        Cloudify.cloudifyOutputColumns(cloudQuery);
        Cloudify.cloudifyOutputFunctions(cloudQuery);
        Cloudify.cloudifyUDFs(cloudQuery);
        Cloudify.cloudifyLimit(cloudQuery);

        try {
            SQLQueryParser.parse(cloudQuery.sqlQuery.toSQLString());
            SQLQueryParser.parse(cloudQuery.rootQuery.toSQLString());
            SQLQueryParser.parse(cloudQuery.internalQuery.toSQLString());
            SQLQueryParser.parse(cloudQuery.leafQuery.toSQLString());
        }
        catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

        return cloudQuery;
    }

    public static void addComplexFunction(String function, ArrayList<String> primitives) throws Exception {

        if (Cloudify.isPrimitive(function)) {
            throw new Exception("Cannot define primitive aggregation functions");
        }

        for (String primitive : primitives) {
            if (!Cloudify.isPrimitive(primitive) && !Cloudify.isComplexFunction(primitive)) {
                throw new Exception(primitive + " is not a primitive or meta-primitive");
            }
        }

        Cloudify.complexFunctions.put(function, primitives);
    }

    public static void addUDF(String udf, float weight) {
        OutputFunction udfFunc = new OutputFunction();
        udfFunc.functionName = udf;
        udfFunc.weight = weight;
        Cloudify.udfs.put(udf, udfFunc);
    }

    public static float getWeightOf(String func) {

        float totalWeight = 0.0f;

        if (Cloudify.isPrimitive(func)) {
            totalWeight = Cloudify.weights.get(func);
        }
        else if (Cloudify.udfs.containsKey(func)) {
            totalWeight = Cloudify.udfs.get(func).weight;
        }
        else {
            for (String function : Cloudify.complexFunctions.get(func)) {
                totalWeight += getWeightOf(function);
            }
        }

        return totalWeight;
    }

    private static boolean isPrimitive(String function) {
        return Cloudify.primitives.contains(function);
    }

    private static boolean isComplexFunction(String function) {
        return Cloudify.complexFunctions.containsKey(function);
    }

    private static HashMap<String, Integer> aliases = new HashMap<String, Integer>();

    private static String generateAlias(String aggregationFunction) {
        if (Cloudify.aliases.containsKey(aggregationFunction) == false) {
            Cloudify.aliases.put(aggregationFunction, 1);
        }

        String newAlias = aggregationFunction + "_" + Integer.toString(Cloudify.aliases.get(aggregationFunction));

        Cloudify.aliases.put(aggregationFunction, Cloudify.aliases.get(aggregationFunction) + 1);

        return newAlias;
    }

    private static void cloudifyPrimitiveSum(CloudQuery cloudQuery, OutputFunction function) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "sum";
        leafFunction.outputName = Cloudify.generateAlias("sum");
        leafFunction.params.addAll(function.params);
        cloudQuery.leafQuery.outputFunctions.add(leafFunction);

        OutputFunction internalFunction = new OutputFunction();
        internalFunction.functionName = "sum";
        internalFunction.outputName = Cloudify.generateAlias("sum");
        Column internalColumn = new Column();
        internalColumn.tableAlias = leafFunction.outputName;
        internalFunction.params.add(internalColumn);
        cloudQuery.internalQuery.outputFunctions.add(internalFunction);

        OutputFunction rootFunction = new OutputFunction();
        rootFunction.functionName = "sum";
        Column rootColumn = new Column();
        rootColumn.tableAlias = internalFunction.outputName;

        /* Keep the output name, if it exists */

        if (function.outputName == null) {
            rootFunction.outputName = Cloudify.generateAlias("sum");
        }
        else {
            rootFunction.outputName = function.outputName;

            /* Rename it in group by and order by */

            /* Replace outputName with temporary name */

            Cloudify.renameFunctionNameInGroudByOrderBy(cloudQuery, function, leafFunction, internalFunction);

        }

        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveCount(CloudQuery cloudQuery, OutputFunction function) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "count";
        leafFunction.outputName = Cloudify.generateAlias("count");
        leafFunction.params.addAll(function.params);
        cloudQuery.leafQuery.outputFunctions.add(leafFunction);

        OutputFunction internalFunction = new OutputFunction();
        internalFunction.functionName = "sum";
        internalFunction.outputName = Cloudify.generateAlias("count");
        Column internalColumn = new Column();
        internalColumn.tableAlias = leafFunction.outputName;
        internalFunction.params.add(internalColumn);
        cloudQuery.internalQuery.outputFunctions.add(internalFunction);

        OutputFunction rootFunction = new OutputFunction();
        rootFunction.functionName = "sum";
        Column rootColumn = new Column();
        rootColumn.tableAlias = internalFunction.outputName;

        /* Keep the output name, if it exists */

        if (function.outputName == null) {
            rootFunction.outputName = Cloudify.generateAlias("count");
        }
        else {
            rootFunction.outputName = function.outputName;

            Cloudify.renameFunctionNameInGroudByOrderBy(cloudQuery, function, leafFunction, internalFunction);
        }

        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveMin(CloudQuery cloudQuery, OutputFunction function) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "min";
        leafFunction.outputName = Cloudify.generateAlias("min");
        leafFunction.params.addAll(function.params);
        cloudQuery.leafQuery.outputFunctions.add(leafFunction);

        OutputFunction internalFunction = new OutputFunction();
        internalFunction.functionName = "min";
        internalFunction.outputName = Cloudify.generateAlias("min");
        Column internalColumn = new Column();
        internalColumn.tableAlias = leafFunction.outputName;
        internalFunction.params.add(internalColumn);
        cloudQuery.internalQuery.outputFunctions.add(internalFunction);

        OutputFunction rootFunction = new OutputFunction();
        rootFunction.functionName = "min";
        Column rootColumn = new Column();
        rootColumn.tableAlias = internalFunction.outputName;

        /* Keep the output name, if it exists */

        if (function.outputName == null) {
            rootFunction.outputName = Cloudify.generateAlias("min");
        }
        else {
            rootFunction.outputName = function.outputName;

            Cloudify.renameFunctionNameInGroudByOrderBy(cloudQuery, function, leafFunction, internalFunction);
        }

        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveMax(CloudQuery cloudQuery, OutputFunction function) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "max";
        leafFunction.outputName = Cloudify.generateAlias("max");
        leafFunction.params.addAll(function.params);
        cloudQuery.leafQuery.outputFunctions.add(leafFunction);

        OutputFunction internalFunction = new OutputFunction();
        internalFunction.functionName = "max";
        internalFunction.outputName = Cloudify.generateAlias("max");
        Column internalColumn = new Column();
        internalColumn.tableAlias = leafFunction.outputName;
        internalFunction.params.add(internalColumn);
        cloudQuery.internalQuery.outputFunctions.add(internalFunction);

        OutputFunction rootFunction = new OutputFunction();
        rootFunction.functionName = "max";
        Column rootColumn = new Column();
        rootColumn.tableAlias = internalFunction.outputName;

        /* Keep the output name, if it exists */

        if (function.outputName == null) {
            rootFunction.outputName = Cloudify.generateAlias("max");
        }
        else {
            rootFunction.outputName = function.outputName;

            Cloudify.renameFunctionNameInGroudByOrderBy(cloudQuery, function, leafFunction, internalFunction);
        }

        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void renameFunctionNameInGroudByOrderBy(CloudQuery cloudQuery, OutputFunction function, OutputFunction leafFunction, OutputFunction internalFunction) {
        for (int i = 0; i < cloudQuery.leafQuery.orderBy.size(); i++) {
            if (cloudQuery.leafQuery.orderBy.get(i).tableAlias.compareTo(function.outputName) == 0) {
                Column newLeafColumn = new Column(cloudQuery.leafQuery.orderBy.get(i));
                newLeafColumn.tableAlias = leafFunction.outputName;
                newLeafColumn.columnName = null;
                cloudQuery.leafQuery.orderBy.remove(i);
                cloudQuery.leafQuery.orderBy.add(i, newLeafColumn);

                Column newInternalColumn = new Column(cloudQuery.internalQuery.orderBy.get(i));
                newInternalColumn.tableAlias = internalFunction.outputName;
                newInternalColumn.columnName = null;
                cloudQuery.internalQuery.orderBy.remove(i);
                cloudQuery.internalQuery.orderBy.add(i, newInternalColumn);

                break;
            }
        }

        for (int i = 0; i < cloudQuery.leafQuery.groupBy.size(); i++) {
            if (cloudQuery.leafQuery.groupBy.get(i).tableAlias.compareTo(function.outputName) == 0) {
                Column newLeafColumn = new Column(cloudQuery.leafQuery.groupBy.get(i));
                newLeafColumn.tableAlias = leafFunction.functionName;
                newLeafColumn.columnName = null;
                cloudQuery.leafQuery.groupBy.remove(i);
                cloudQuery.leafQuery.groupBy.add(i, newLeafColumn);

                Column newInternalColumn = new Column(cloudQuery.internalQuery.groupBy.get(i));
                newInternalColumn.tableAlias = internalFunction.functionName;
                newInternalColumn.columnName = null;
                cloudQuery.internalQuery.groupBy.remove(i);
                cloudQuery.internalQuery.groupBy.add(i, newInternalColumn);

                break;
            }
        }
    }

    private static void cloudifyOutputFunctions(CloudQuery cloudQuery) throws Exception {

        Cloudify.reduceToPrimitives(cloudQuery.sqlQuery.outputFunctions);

        for (OutputFunction func : cloudQuery.sqlQuery.outputFunctions) {

            if (Cloudify.isUDF(func)) {
                continue;
            }
            else if (Cloudify.isPrimitive(func.functionName)) {
                Cloudify.addPrimitive(cloudQuery, func);
            }
            else {
                throw new Exception (func.functionName + " is not a primitive, a UDF, or a known complex function");
            }
        }

        Cloudify.aliases.clear();
    }

    private static void reduceToPrimitives(List<OutputFunction> outputFunctions) {

        for (int i = 0; i < outputFunctions.size(); ) {

           OutputFunction func = outputFunctions.get(i);

            if (Cloudify.isUDF(func)) {
                i++;
                continue;
            }

            if (Cloudify.isPrimitive(func.functionName)) {
                i++;
                continue;
            }

            if (Cloudify.isComplexFunction(func.functionName)) {
                List<OutputFunction> reduction = Cloudify.reduceComplexFuncToPrimitives(func);
                outputFunctions.remove(i);
                outputFunctions.addAll(reduction);
            }
        }
    }

    private static List<OutputFunction> reduceComplexFuncToPrimitives(OutputFunction func) {

        List<String> reductions = Cloudify.complexFunctions.get(func.functionName);

        /* Find the primitive reduction */

        for (int i = 0; i < reductions.size(); ) {

            String comp = reductions.get(i);

            if (Cloudify.isPrimitive(comp)) {
                i++;
                continue;
            }

            if (Cloudify.isComplexFunction(comp)) {
                reductions.remove(i);
                reductions.addAll(Cloudify.complexFunctions.get(comp));
            }
        }

        List<OutputFunction> primitives = new ArrayList<OutputFunction>();

        for (String reduction : reductions) {

            OutputFunction primitive = new OutputFunction();
            primitive.functionName = reduction;
            primitive.params = func.params;
            primitive.outputName = func.outputName;

            primitives.add(primitive);
        }

        return primitives;
    }

    private static void addPrimitive(CloudQuery cloudQuery, OutputFunction function) {
        if (function.functionName.compareTo("count") == 0) {
            Cloudify.cloudifyPrimitiveCount(cloudQuery, function);
        }
        else if (function.functionName.compareTo("sum") == 0) {
            Cloudify.cloudifyPrimitiveSum(cloudQuery, function);
        }
        else if (function.functionName.compareTo("min") == 0) {
            Cloudify.cloudifyPrimitiveMin(cloudQuery, function);
        }
        else if (function.functionName.compareTo("max") == 0) {
            Cloudify.cloudifyPrimitiveMax(cloudQuery, function);
        }
    }

    private static void cloudifyOutputColumns(CloudQuery cloudQuery) {
        cloudQuery.leafQuery.outputColumns = cloudQuery.sqlQuery.outputColumns;

        for (OutputColumn outputColumn : cloudQuery.sqlQuery.outputColumns) {
            OutputColumn internalOutputColumn = new OutputColumn(outputColumn);
            internalOutputColumn.column.columnName = outputColumn.outputName;
            internalOutputColumn.outputName = outputColumn.outputName;
            internalOutputColumn.column.tableAlias = null;
            cloudQuery.internalQuery.outputColumns.add(internalOutputColumn);
        }

        for (OutputColumn outputColumn : cloudQuery.sqlQuery.outputColumns) {
            OutputColumn rootOutputColumn = new OutputColumn(outputColumn);
            rootOutputColumn.outputName = outputColumn.outputName;
            rootOutputColumn.column.columnName = outputColumn.outputName;
            rootOutputColumn.column.tableAlias = null;
            cloudQuery.rootQuery.outputColumns.add(rootOutputColumn);
        }
    }

    private static void cloudifyGroupBy(CloudQuery cloudQuery) {
        for(Column column : cloudQuery.sqlQuery.groupBy) {
            cloudQuery.leafQuery.groupBy.add(new Column(column));
            cloudQuery.internalQuery.groupBy.add((new Column(column)));
            cloudQuery.rootQuery.groupBy.add(new Column(column));
        }
    }

    private static void cloudifyOrderBy(CloudQuery cloudQuery) {
        for(Column column : cloudQuery.sqlQuery.orderBy) {
            cloudQuery.leafQuery.orderBy.add(new Column(column));
            cloudQuery.internalQuery.orderBy.add((new Column(column)));
            cloudQuery.rootQuery.orderBy.add(new Column(column));
        }
    }

    private static void cloudifyLimit(CloudQuery cloudQuery) {
        cloudQuery.leafQuery.limit= cloudQuery.sqlQuery.limit;
        cloudQuery.internalQuery.limit = cloudQuery.sqlQuery.limit;
        cloudQuery.rootQuery.limit = cloudQuery.sqlQuery.limit;
    }

    private static void cloudifyUDFs(CloudQuery cloudQuery) {

        for (OutputFunction func : cloudQuery.sqlQuery.outputFunctions) {

            if (Cloudify.isUDF(func)) {
                cloudQuery.leafQuery.outputFunctions.add(func);
                cloudQuery.internalQuery.outputFunctions.add(func);
                cloudQuery.rootQuery.outputFunctions.add(func);
            }
        }
    }

    private static boolean isUDF(OutputFunction func) {
        return Cloudify.udfs.containsKey(func.functionName);
    }
}

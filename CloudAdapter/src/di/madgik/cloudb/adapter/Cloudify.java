package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Cloudify {

    private static HashMap<String, ArrayList<String>> rules = new HashMap<String, ArrayList<String>>();

    public static CloudQuery toCloud(SQLQuery query) throws Exception {
        CloudQuery cloudQuery = new CloudQuery(query);

        cloudQuery.leafQuery.inputTables = cloudQuery.sqlQuery.inputTables;
        cloudQuery.leafQuery.joins = cloudQuery.sqlQuery.joins;
        cloudQuery.leafQuery.filters = cloudQuery.sqlQuery.filters;

        Cloudify.cloudifyGroupBy(cloudQuery);
        Cloudify.cloudifyOrderBy(cloudQuery);
        Cloudify.cloudifyOutputColumns(cloudQuery);
        Cloudify.cloudifyOutputFunctions(cloudQuery);
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

    private static boolean isPrimitive(String primitive) {
        if (primitive.compareTo("sum") != 0 && primitive.compareTo("count") != 0
                && primitive.compareTo("min") != 0 && primitive.compareTo("max") != 0) {
            return false;
        }

        return true;
    }

    public static void addRule(String function, ArrayList<String> primitives) throws Exception {

        if (Cloudify.isPrimitive(function)) {
            throw new Exception("Cannot define primitive aggregation functions");
        }

        for (String primitive : primitives) {
            if (Cloudify.isPrimitive(primitive) == false) {
                throw new Exception(primitive + " is not a primitive");
            }
        }

        Cloudify.rules.put(function, primitives);
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

        for (OutputFunction aggregationFunction : cloudQuery.sqlQuery.outputFunctions) {

            if (Cloudify.isPrimitive(aggregationFunction.functionName)) {
                Cloudify.addPrimitive(cloudQuery, aggregationFunction);
            }
            else if (Cloudify.rules.keySet().contains(aggregationFunction.functionName)) {

                for (String primitive : Cloudify.rules.get(aggregationFunction.functionName)) {
                    OutputFunction primitiveFunction = new OutputFunction();
                    primitiveFunction.functionName = primitive;
                    primitiveFunction.params.addAll(aggregationFunction.params);
                    primitiveFunction.outputName = aggregationFunction.outputName;
                    Cloudify.addPrimitive(cloudQuery, primitiveFunction);
                }
            }
            else {
                throw new Exception (aggregationFunction.functionName + " is not a primitive, nor a known complex function");
            }
        }

        Cloudify.aliases.clear();
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
            internalOutputColumn.outputName = outputColumn.column.columnName;
            internalOutputColumn.column.columnName = outputColumn.outputName;
            internalOutputColumn.column.tableAlias = null;
            cloudQuery.internalQuery.outputColumns.add(internalOutputColumn);
        }

        for (OutputColumn outputColumn : cloudQuery.sqlQuery.outputColumns) {
            OutputColumn rootOutputColumn = new OutputColumn(outputColumn);
            rootOutputColumn.outputName = outputColumn.column.columnName;
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
}

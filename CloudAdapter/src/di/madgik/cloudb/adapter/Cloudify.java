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

        cloudQuery.leafQuery.outputColumns = cloudQuery.sqlQuery.outputColumns;
        cloudQuery.leafQuery.inputTables = cloudQuery.sqlQuery.inputTables;
        cloudQuery.leafQuery.joins = cloudQuery.sqlQuery.joins;
        cloudQuery.leafQuery.filters = cloudQuery.sqlQuery.filters;

        Cloudify.cloudifyOutputFunctions(cloudQuery);
        Cloudify.cloudifyGroupBy(cloudQuery);
        Cloudify.cloudifyOrderBy(cloudQuery);
        Cloudify.cloudifyLimit(cloudQuery);

        try {
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


    private static void cloudifyPrimitiveSum(CloudQuery cloudQuery, List<Column> parameters) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "sum";
        leafFunction.outputName = Cloudify.generateAlias("sum");
        leafFunction.params.addAll(parameters);
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
        rootFunction.outputName = Cloudify.generateAlias("sum");
        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveCount(CloudQuery cloudQuery, List<Column> parameters) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "count";
        leafFunction.outputName = Cloudify.generateAlias("count");
        leafFunction.params.addAll(parameters);
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
        rootFunction.outputName = Cloudify.generateAlias("count");
        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveMin(CloudQuery cloudQuery, List<Column> parameters) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "min";
        leafFunction.outputName = Cloudify.generateAlias("min");
        leafFunction.params.addAll(parameters);
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
        rootFunction.outputName = Cloudify.generateAlias("min");
        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyPrimitiveMax(CloudQuery cloudQuery, List<Column> parameters) {
        OutputFunction leafFunction = new OutputFunction();
        leafFunction.functionName = "max";
        leafFunction.outputName = Cloudify.generateAlias("max");
        leafFunction.params.addAll(parameters);
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
        rootFunction.outputName = Cloudify.generateAlias("max");
        rootFunction.params.add(rootColumn);
        cloudQuery.rootQuery.outputFunctions.add(rootFunction);
    }

    private static void cloudifyOutputFunctions(CloudQuery cloudQuery) throws Exception {

        for (OutputFunction aggregationFunction : cloudQuery.sqlQuery.outputFunctions) {

            if (Cloudify.isPrimitive(aggregationFunction.functionName)) {
                Cloudify.addPrimitive(cloudQuery, aggregationFunction.functionName, aggregationFunction.params);
            }
            else if (Cloudify.rules.keySet().contains(aggregationFunction.functionName)) {

                for (String primitive : Cloudify.rules.get(aggregationFunction.functionName)) {
                    Cloudify.addPrimitive(cloudQuery, primitive, aggregationFunction.params);
                }
            }
            else {
                throw new Exception (aggregationFunction.functionName + " is not a primitive, nor a known complex function");
            }
        }

        Cloudify.aliases.clear();
    }

    private static void addPrimitive(CloudQuery cloudQuery, String function, List<Column> parameters) {
        if (function.compareTo("count") == 0) {
            Cloudify.cloudifyPrimitiveCount(cloudQuery, parameters);
        }
        else if (function.compareTo("sum") == 0) {
            Cloudify.cloudifyPrimitiveSum(cloudQuery, parameters);
        }
        else if (function.compareTo("min") == 0) {
            Cloudify.cloudifyPrimitiveMin(cloudQuery, parameters);
        }
        else if (function.compareTo("max") == 0) {
            Cloudify.cloudifyPrimitiveMax(cloudQuery, parameters);
        }
    }

    private static void cloudifyGroupBy(CloudQuery cloudQuery) {
        cloudQuery.leafQuery.groupBy = cloudQuery.sqlQuery.groupBy;
        cloudQuery.internalQuery.groupBy = cloudQuery.sqlQuery.groupBy;
        cloudQuery.rootQuery.groupBy = cloudQuery.sqlQuery.groupBy;
    }

    private static void cloudifyOrderBy(CloudQuery cloudQuery) {
        cloudQuery.leafQuery.orderBy = cloudQuery.sqlQuery.orderBy;
        cloudQuery.internalQuery.orderBy = cloudQuery.sqlQuery.orderBy;
        cloudQuery.rootQuery.orderBy = cloudQuery.sqlQuery.orderBy;
    }

    private static void cloudifyLimit(CloudQuery cloudQuery) {
        cloudQuery.leafQuery.limit= cloudQuery.sqlQuery.limit;
        cloudQuery.internalQuery.limit = cloudQuery.sqlQuery.limit;
        cloudQuery.rootQuery.limit = cloudQuery.sqlQuery.limit;
    }
}

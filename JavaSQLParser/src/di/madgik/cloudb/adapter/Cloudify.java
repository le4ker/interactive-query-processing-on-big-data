package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.Column;
import com.foundationdb.sql.query.OutputFunction;
import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.Table;

import java.util.HashMap;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Cloudify {

    public static CloudQuery toCloud(SQLQuery query) {
        CloudQuery cloudQuery = new CloudQuery(query);

        cloudQuery.leafQuery.outputColumns = cloudQuery.sqlQuery.outputColumns;
        cloudQuery.leafQuery.inputTables = cloudQuery.sqlQuery.inputTables;
        cloudQuery.leafQuery.joins = cloudQuery.sqlQuery.joins;
        cloudQuery.leafQuery.filters = cloudQuery.sqlQuery.filters;

        Cloudify.cloudifyOutputFunctions(cloudQuery);
        Cloudify.cloudifyGroupBy(cloudQuery);
        Cloudify.cloudifyOrderBy(cloudQuery);
        Cloudify.cloudifyLimit(cloudQuery);
        
        return cloudQuery;
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

    private static void cloudifyOutputFunctions(CloudQuery cloudQuery) {
        for (OutputFunction aggregationFunction : cloudQuery.sqlQuery.outputFunctions) {
            OutputFunction newFunction = new OutputFunction();

            if (aggregationFunction.functionName.compareTo("count") == 0) {
                OutputFunction leafFunction = new OutputFunction();
                leafFunction.functionName = "count";
                leafFunction.outputName = Cloudify.generateAlias("count");
                leafFunction.params.addAll(aggregationFunction.params);
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
                rootFunction.params.add(rootColumn);
                cloudQuery.rootQuery.outputFunctions.add(rootFunction);
            }
            else if (aggregationFunction.functionName.compareTo("sum") == 0) {
                OutputFunction leafFunction = new OutputFunction();
                leafFunction.functionName = "sum";
                leafFunction.outputName = Cloudify.generateAlias("sum");
                leafFunction.params.addAll(aggregationFunction.params);
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
                rootFunction.params.add(rootColumn);
                cloudQuery.rootQuery.outputFunctions.add(rootFunction);
            }
            else if (aggregationFunction.functionName.compareTo("min") == 0) {
                OutputFunction leafFunction = new OutputFunction();
                leafFunction.functionName = "min";
                leafFunction.outputName = Cloudify.generateAlias("min");
                leafFunction.params.addAll(aggregationFunction.params);
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
                rootFunction.params.add(rootColumn);
                cloudQuery.rootQuery.outputFunctions.add(rootFunction);
            }
            else if (aggregationFunction.functionName.compareTo("max") == 0) {
                OutputFunction leafFunction = new OutputFunction();
                leafFunction.functionName = "max";
                leafFunction.outputName = Cloudify.generateAlias("max");
                leafFunction.params.addAll(aggregationFunction.params);
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
                rootFunction.params.add(rootColumn);
                cloudQuery.rootQuery.outputFunctions.add(rootFunction);
            }
            else if (aggregationFunction.functionName.compareTo("avg") == 0) {

                /* avg(x) = sum(x) / count(x) */

                OutputFunction sumLeafFunction = new OutputFunction();
                sumLeafFunction.functionName = "sum";
                sumLeafFunction.params.addAll(aggregationFunction.params);
                sumLeafFunction.outputName = Cloudify.generateAlias("sum");
                cloudQuery.leafQuery.outputFunctions.add(sumLeafFunction);

                OutputFunction countLeafFunction = new OutputFunction();
                countLeafFunction.functionName = "count";
                countLeafFunction.params.addAll(aggregationFunction.params);
                countLeafFunction.outputName = Cloudify.generateAlias("count");
                cloudQuery.leafQuery.outputFunctions.add(countLeafFunction);

                OutputFunction sumInternalFunction = new OutputFunction();
                sumInternalFunction.functionName = "sum";
                Column sumInternalColumn = new Column();
                sumInternalColumn.tableAlias = sumLeafFunction.outputName;
                sumInternalFunction.outputName = Cloudify.generateAlias("sum");
                sumInternalFunction.params.add(sumInternalColumn);

                OutputFunction countInternalFunction = new OutputFunction();
                countInternalFunction.functionName = "count";
                Column countInternalColumn = new Column();
                countInternalColumn.tableAlias = countLeafFunction.outputName;
                countInternalFunction.outputName = Cloudify.generateAlias("count");
                countInternalFunction.params.add(countInternalColumn);

                OutputFunction sumRootFunction = new OutputFunction();
                sumRootFunction.functionName = "sum";
                Column sumRootColumn = new Column();
                sumRootColumn.tableAlias = sumInternalFunction.outputName;
                sumRootFunction.params.add(sumRootColumn);

                OutputFunction countRootFunction = new OutputFunction();
                countRootFunction.functionName = "count";
                Column countRootColumn = new Column();
                countRootColumn.tableAlias = countInternalFunction.outputName;
                countRootFunction.params.add(countRootColumn);

                // TODO divide sum with cont in root level
            }
        }

        Cloudify.aliases.clear();
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

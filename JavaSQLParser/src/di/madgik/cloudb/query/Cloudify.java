package di.madgik.cloudb.query;

import com.foundationdb.sql.query.OutputFunction;
import com.foundationdb.sql.query.SQLQuery;

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

    private static void cloudifyOutputFunctions(CloudQuery cloudQuery) {
        for (OutputFunction aggregationFunction : cloudQuery.sqlQuery.outputFunctions) {
            switch (aggregationFunction.functionName) {
                case "count":

                    break;
                case "sum":

                    break;
                case "min":

                    break;
                case "max":

                    break;
            }
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

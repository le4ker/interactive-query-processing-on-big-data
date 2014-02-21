package di.madgik.cloudb.query;

import com.foundationdb.sql.query.SQLQuery;

/**
 * Created by panossakkos on 2/21/14.
 */
public class Cloudify {

    public static CloudQuery toCloud(SQLQuery query) {
        CloudQuery cloudQuery = new CloudQuery(query);

        /* Leaf-level input tables, joins and filters are the query's filters */

        cloudQuery.leafQuery.inputTables = cloudQuery.sqlQuery.inputTables;
        cloudQuery.leafQuery.joins = cloudQuery.sqlQuery.joins;
        cloudQuery.leafQuery.filters = cloudQuery.sqlQuery.filters;

        Cloudify.cloudifyGroupBy(cloudQuery);
        Cloudify.cloudifyOrderBy(cloudQuery);
        Cloudify.cloudifyLimit(cloudQuery);

        return cloudQuery;
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

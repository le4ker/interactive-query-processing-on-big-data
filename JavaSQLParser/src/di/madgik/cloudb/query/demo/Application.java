package di.madgik.cloudb.query.demo;

import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.SQLQueryParser;
import di.madgik.cloudb.query.CloudQuery;
import di.madgik.cloudb.query.Cloudify;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Application {

    private static String query = " select l.orderkey " +
                                  " from lineitem l " +
                                  " where l.orerkey > 100";

    public static void main (String []args) throws Exception {

        SQLQuery query = SQLQueryParser.parse(Application.query);

        CloudQuery cloudQuery = Cloudify.toCloud(query);

        System.out.println(cloudQuery);
    }
}

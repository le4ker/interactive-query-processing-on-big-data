package di.madgik.cloudb.query;

import com.foundationdb.sql.query.SQLQuery;

/**
 * Created by panossakkos on 2/21/14.
 */

public class CloudQuery extends SQLQuery {

    private RootQuery rootQuery;
    private InternalQuery internalQuery;
    private LeafQuery leafQuery;

}

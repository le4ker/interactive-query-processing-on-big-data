package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.SQLQuery;

/**
 * Created by panossakkos on 2/21/14.
 */

public class LeafQuery extends SQLQuery {

    @Override
    public String toString() {
        return super.toString().replace("\n", "\n\t");
    }
}

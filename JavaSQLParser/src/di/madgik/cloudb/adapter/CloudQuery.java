package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.SQLQuery;

/**
 * Created by panossakkos on 2/21/14.
 */

public class CloudQuery {

    protected final SQLQuery sqlQuery;

    public RootQuery rootQuery = new RootQuery();
    public InternalQuery internalQuery = new InternalQuery();
    public LeafQuery leafQuery = new LeafQuery();

    public CloudQuery(SQLQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public String toString () {
        StringBuilder output = new StringBuilder();

        output.append("SQL query:");
        output.append(this.sqlQuery);
        output.append("\nRoot query:");
        output.append(this.rootQuery);
        output.append("\nInternal query:");
        output.append(this.internalQuery);
        output.append("\nLeaf query:");
        output.append(this.leafQuery);

        return output.toString();
    }
}

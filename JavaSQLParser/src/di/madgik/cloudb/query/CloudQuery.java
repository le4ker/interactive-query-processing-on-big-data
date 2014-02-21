package di.madgik.cloudb.query;

import com.foundationdb.sql.query.SQLQuery;

/**
 * Created by panossakkos on 2/21/14.
 */

public class CloudQuery extends SQLQuery {

    private RootQuery rootQuery;
    private InternalQuery internalQuery;
    private LeafQuery leafQuery;

    @Override
    public String toString () {
        StringBuilder output = new StringBuilder();

        output.append("Root query:\n");
        output.append(rootQuery);
        output.append("\nInternal query:\n");
        output.append(internalQuery);
        output.append("\nLeaf query:\n");
        output.append(leafQuery);

        return output.toString();
    }
}

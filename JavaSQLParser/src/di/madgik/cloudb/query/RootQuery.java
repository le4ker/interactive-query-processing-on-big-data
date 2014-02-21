package di.madgik.cloudb.query;

import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.Table;

/**
 * Created by panossakkos on 2/21/14.
 */

public class RootQuery extends SQLQuery {

    public RootQuery () {
        Table internalTable = new Table();
        internalTable.name = "Leaf Level";

        this.inputTables.add(internalTable);
    }

    @Override
    public String toString() {
        return super.toString().replace("\n", "\n\t");
    }
}

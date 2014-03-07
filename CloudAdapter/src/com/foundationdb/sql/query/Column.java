/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi
 */
public class Column {

    public String tableAlias = null;
    public String columnName = null;

    @Override
    public String toString() {
        if(this.tableAlias != null && this.columnName != null) {
            return this.tableAlias + "." + columnName;
        }
        else if (this.tableAlias == null) {
            return this.columnName;
        }
        else {
            return this.tableAlias;
        }
    }
}

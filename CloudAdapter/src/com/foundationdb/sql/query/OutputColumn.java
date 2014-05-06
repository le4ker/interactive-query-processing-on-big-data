/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi, panossakkos
 */
public class OutputColumn {

  public Column column = new Column();
  public String outputName = null;

    public OutputColumn() {}

    public OutputColumn(OutputColumn outputColumn) {
        this.column = new Column(outputColumn.column);
        this.outputName = new String(outputColumn.outputName);
    }

    @Override
    public String toString() {
        if (this.outputName == null) {
            return column.toString();
        }
        else {
            return column.toString() + " as " + outputName;
        }
    }
}

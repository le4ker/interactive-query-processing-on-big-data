/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi
 */
public class OutputColumn {

  public Column column = new Column();
  public String outputName = null;

  @Override
  public String toString() {
    return column.toString() + " as " + outputName;
  }
}

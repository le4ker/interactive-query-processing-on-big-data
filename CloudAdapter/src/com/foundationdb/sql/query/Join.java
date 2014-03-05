/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi
 */
public class Join {

  public String leftTableAlias = null;
  public String leftColumnName = null;
  public String rightTableAlias = null;
  public String rightColumnName = null;

  @Override
  public String toString() {
    return leftTableAlias + "." + leftColumnName + " = " + rightTableAlias + "." + rightColumnName;
  }
}

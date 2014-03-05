/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi
 */
public class Filter {

  public String tableAlias = null;
  public String columnName = null;
  public String operator = null;
  public String value = null;

  @Override
  public String toString() {
    return tableAlias + "." + columnName + " " + operator + " " + value;
  }
}

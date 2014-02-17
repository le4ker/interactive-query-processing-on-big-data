/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author heraldkllapi
 */
public class OutputFunction {
  public String functionName;
  public List<Column> params = new ArrayList<Column>();
  public String outputName;

  @Override
  public String toString() {
    return functionName + "(" + params.toString() + ") as " + outputName;
  }
}

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
public class SQLQuery {
  public List<OutputColumn> outputColumns = new ArrayList<OutputColumn>();
  public List<OutputFunction> outputFunctions = new ArrayList<OutputFunction>();
  public List<Table> inputTables = new ArrayList<Table>();
  public List<Filter> filters = new ArrayList<Filter>();
  public List<Join> joins = new ArrayList<Join>();
  public List<Column> groupBy = new ArrayList<Column>();
  public List<Column> orderBy = new ArrayList<Column>();
  public int limit = -1;

  public SQLQuery() {
    
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    // Print project columns
    for (OutputColumn c : outputColumns) {
      output.append("\n\t" + c.toString());
    }
    for (OutputFunction f : outputFunctions) {
      output.append("\n\t" + f.toString());
    }
    output.append("\nInput");
    for (Table t : inputTables) {
      output.append("\n\t" + t.toString());
    }
    output.append("\nFilters");
    for (Filter f : filters) {
      output.append("\n\t" + f.toString());
    }
    output.append("\nJoins");
    for (Join j : joins) {
      output.append("\n\t" + j.toString());
    }
    output.append("\nGroup By");
    for (Column c : groupBy) {
      output.append("\n\t" + c.toString());
    }
    output.append("\nOrder By");
    for (Column c : orderBy) {
      output.append("\n\t" + c.toString());
    }
    output.append("\nLimit");
    output.append("\n\t" + limit);
    return output.toString();
  }
}

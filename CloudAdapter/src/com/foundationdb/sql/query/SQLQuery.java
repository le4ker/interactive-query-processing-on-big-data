/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author heraldkllapi, panossakkos
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

    public SQLQuery() { }

    public String toSQLString() {

        StringBuilder output = new StringBuilder();

        if (outputColumns.size() == 0 && outputFunctions.size() == 0) {
            output.append("SELECT * ");
        }
        else {
            output.append("SELECT ");

            boolean moreThanOne = false;

            for (OutputColumn c : outputColumns) {
                if (moreThanOne) {
                    output.append(", " + c.toString());
                }
                else {
                    output.append(" " + c.toString());
                }
                moreThanOne = true;
            }
            for (OutputFunction f : outputFunctions) {
                if (moreThanOne) {
                    output.append(", " + f.toString());
                }
                else {
                    output.append(" " + f.toString());
                }
                moreThanOne = true;
            }
        }

        if (inputTables.size() > 0) {
            output.append(" FROM ");

            boolean moreThanOne = false;

            for (Table t : inputTables) {
                if (moreThanOne) {
                    output.append(", " + t.toString());
                }
                else {
                    output.append(" " + t.toString());
                }
                moreThanOne = true;
            }
        }

        if (filters.size() > 0 || joins.size() > 0) {
            output.append(" WHERE ");


            boolean moreThanOne = false;

            for (Filter f : filters) {
                if (moreThanOne) {
                    output.append(" AND " + f.toString());
                }
                else {
                    output.append(" " + f.toString());
                }

                moreThanOne = true;
            }

            for (Join j : joins) {

                if (moreThanOne) {
                    output.append(" AND " + j.toString());
                }
                else {
                    output.append(" " + j.toString());
                }

                moreThanOne = true;
            }
        }

        if (groupBy.size() > 0) {
            output.append(" GROUP BY ");

            boolean moreThanOne = false;

            for (Column c : groupBy) {
                if (moreThanOne) {
                    output.append(", " + c.toString());
                }
                else {
                    output.append(" " + c.toString());
                }
                moreThanOne = true;
            }
        }

        if (orderBy.size() > 0) {
            output.append(" ORDER BY ");

            boolean moreThanOne = false;

            for (Column c : orderBy) {
                if (moreThanOne) {
                    output.append(", " + c.toString());
                }
                else {
                    output.append(" " + c.toString());
                }
                moreThanOne = true;
            }
        }

        if (limit != -1) {
            output.append(" LIMIT ");
            output.append(" " + limit);
        }

        return output.toString();
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

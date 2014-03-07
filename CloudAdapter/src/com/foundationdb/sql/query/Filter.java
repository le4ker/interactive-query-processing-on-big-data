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
        if (this.containsOnlyDigits() == false) {
            return tableAlias + "." + columnName + " " + operator + " '" + value + "'";
        }

        return tableAlias + "." + columnName + " " + operator + " " + value;
    }

    private boolean containsOnlyDigits() {
        boolean onlyDigits = true;

        for (int i = 0; i < this.value.length(); i++) {
            if (this.value.charAt(i) < '0' || this.value.charAt(i) >'9') {
                onlyDigits = false;
            }
        }

        return onlyDigits;
    }
}

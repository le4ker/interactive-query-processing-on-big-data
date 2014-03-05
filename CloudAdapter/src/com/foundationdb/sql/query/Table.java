/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

/**
 *
 * @author heraldkllapi
 */
public class Table {
  public String name;
  public String alias;

  public Table() {
    
  }
  
  @Override
  public String toString() {
    return name + " " + alias;
  }
}

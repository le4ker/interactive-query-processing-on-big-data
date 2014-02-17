/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.demo;

import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.SQLQueryParser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author heraldkllapi
 */
public class DemoMain {

  public static void main(String[] args) throws Exception {
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.OFF);

    SQLQuery query = SQLQueryParser.parse(
        " select a1.id as x, " +
        "        b1.name as z, " +
        "        sum(b1.name) as w1, " +
        "        count(a1.id) as w2, " +
        "        max(a1.id) as w3, " +
        "        f(b1.name, a1.id, c1.id) as w4" +
        " from a a1, b b1, c c1" +
        " where a1.id > 0" +
        "   and b1.name = 'hello'" +
        "   and a1.id = b1.id" +
        "   and b1.id = c1.id" +
        " group by b1.name, a1.name" +
        " order by b1.id, b1.name" +
        " limit 10"
    );

    System.out.println(query.toString());
  }
}

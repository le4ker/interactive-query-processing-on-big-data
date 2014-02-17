/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query;

import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;
import com.foundationdb.sql.query.visitors.SQLQueryVisitor;

/**
 *
 * @author heraldkllapi
 */
public class SQLQueryParser {

  public static SQLQuery parse(String queryString) throws Exception {
    SQLParser parser = new SQLParser();
    StatementNode node = parser.parseStatement(queryString);
    // Traverse the query tree
    SQLQuery query = new SQLQuery();
    SQLQueryVisitor visitor = new SQLQueryVisitor(query);
    node.accept(visitor);
    return query;
  }
}

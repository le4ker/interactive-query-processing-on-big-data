/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.SQLQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author heraldkllapi
 */
public class SelectVisitor extends AbstractVisitor {
  private static final Logger log = Logger.getLogger(SelectVisitor.class);

  public SelectVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof SelectNode) {
      // Result columns
      ResultColumnsVisitor projectVisitor = new ResultColumnsVisitor(query);
      node.accept(projectVisitor);
      // Input tables
      FromListVisitor fromVisitor = new FromListVisitor(query);
      node.accept(fromVisitor);
      // Where conditions
      WhereClauseVisitor whereVisitor = new WhereClauseVisitor(query);
      node.accept(whereVisitor);
      // Group by
      GroupByListVisitor groupByVisitor = new GroupByListVisitor(query);
      node.accept(groupByVisitor);
      return node;
    }
    log.trace("Skip: " + node.getClass().getSimpleName());
    return node;
  }
}

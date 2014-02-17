/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.ConstantNode;
import com.foundationdb.sql.parser.CursorNode;
import com.foundationdb.sql.parser.FromList;
import com.foundationdb.sql.parser.OrderByList;
import com.foundationdb.sql.parser.SelectNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.SQLQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author heraldkllapi
 */
public class SQLQueryVisitor extends AbstractVisitor {
  private static final Logger log = Logger.getLogger(SQLQueryVisitor.class);

  public SQLQueryVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof SelectNode) {
      SelectVisitor selectVis = new SelectVisitor(query);
      node.accept(selectVis);
      return node;
    }
    if (node instanceof OrderByList) {
      OrderByVisitor orderVisitor = new OrderByVisitor(query);
      node.accept(orderVisitor);
      return node;
    }
    if (node instanceof CursorNode) {
      CursorNode cNode = (CursorNode)node;
      if (cNode.getFetchFirstClause() != null) {
        query.limit = (Integer)((ConstantNode)cNode.getFetchFirstClause()).getValue();
      }
      return node;
    }
    // Limit
//    if ().getFetchFirstClause()
    log.trace("Skip: " + node.getClass().getSimpleName());
    return node;
  }
}

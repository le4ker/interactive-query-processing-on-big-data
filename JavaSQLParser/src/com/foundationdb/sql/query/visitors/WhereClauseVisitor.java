/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.BinaryRelationalOperatorNode;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.ConstantNode;
import com.foundationdb.sql.parser.NumericConstantNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.Filter;
import com.foundationdb.sql.query.Join;
import com.foundationdb.sql.query.SQLQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author heraldkllapi
 */
public class WhereClauseVisitor extends AbstractVisitor {
  private static final Logger log = Logger.getLogger(WhereClauseVisitor.class);

  public WhereClauseVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof BinaryRelationalOperatorNode) {
      BinaryRelationalOperatorNode binOp = (BinaryRelationalOperatorNode)node;
      // Do nothing in the inner nodes of the tree
      if (binOp.getLeftOperand() instanceof ColumnReference == false) {
        return node;
      }
      // Check join
      if (binOp.getRightOperand() instanceof ColumnReference) {
        Join join = new Join();

        ColumnReference left = (ColumnReference)binOp.getLeftOperand();
        join.leftTableAlias = left.getTableName();
        join.leftColumnName = left.getColumnName();
        ColumnReference right = (ColumnReference)binOp.getRightOperand();
        join.rightTableAlias = right.getTableName();
        join.rightColumnName = right.getColumnName();

        query.joins.add(join);
      }
      // Check select
      if (binOp.getRightOperand() instanceof ConstantNode) {
        Filter filter = new Filter();

        ColumnReference left = (ColumnReference)binOp.getLeftOperand();
        filter.tableAlias = left.getTableName();
        filter.columnName = left.getColumnName();
        filter.operator = binOp.getOperator();
        filter.value = ((ConstantNode)binOp.getRightOperand()).getValue().toString();

        query.filters.add(filter);
      }
      return node;
    }
    log.trace("Skip: " + node.getClass().getSimpleName());
    return node;
  }
}

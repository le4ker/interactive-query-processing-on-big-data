/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.OrderByColumn;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.Column;
import com.foundationdb.sql.query.SQLQuery;

/**
 *
 * @author heraldkllapi
 */
public class OrderByColumnVisitor extends AbstractVisitor {

  public OrderByColumnVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof OrderByColumn) {
      Column column = new Column();

      ColumnReference parserColumn = (ColumnReference)((OrderByColumn)node).getExpression();
      column.tableAlias = parserColumn.getTableName();
      column.columnName = parserColumn.getColumnName();

      query.orderBy.add(column);
      return node;
    }
    return node;
  }
}

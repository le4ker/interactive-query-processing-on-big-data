/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.GroupByColumn;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.Column;
import com.foundationdb.sql.query.SQLQuery;

/**
 *
 * @author heraldkllapi, panossakkos
 */
public class GroupByColumnVisitor extends AbstractVisitor {

  public GroupByColumnVisitor(SQLQuery query) {
    super(query);
  }

    @Override
    public Visitable visit(Visitable node) throws StandardException {
        if (node instanceof GroupByColumn) {
            Column column = new Column();

            ColumnReference parserColumn = (ColumnReference)((GroupByColumn)node).getColumnExpression();

            if (column.tableAlias == null ) {
                column.tableAlias = parserColumn.getColumnName();
            }
            else {
                column.tableAlias = parserColumn.getTableName();
                column.columnName = parserColumn.getColumnName();
            }

            query.groupBy.add(column);

            return node;
        }
        return node;
    }
}

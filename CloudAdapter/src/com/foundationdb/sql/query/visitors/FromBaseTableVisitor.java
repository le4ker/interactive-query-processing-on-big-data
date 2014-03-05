/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.FromBaseTable;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.Table;

/**
 *
 * @author heraldkllapi
 */
public class FromBaseTableVisitor extends AbstractVisitor {

  public FromBaseTableVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof FromBaseTable) {
      Table queryTable = new Table();

      FromBaseTable parserTable = (FromBaseTable)node;
      queryTable.name = parserTable.getOrigTableName().getTableName();
      queryTable.alias = parserTable.getExposedName();

      query.inputTables.add(queryTable);
      return node;
    }
    return node;
  }
}

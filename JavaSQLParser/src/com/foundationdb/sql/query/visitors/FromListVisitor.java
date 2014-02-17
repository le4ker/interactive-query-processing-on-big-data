/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.FromList;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.SQLQuery;

/**
 *
 * @author heraldkllapi
 */
public class FromListVisitor extends AbstractVisitor {

  public FromListVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof FromList) {
      FromList from = (FromList)node;
      FromBaseTableVisitor fromVisitor = new FromBaseTableVisitor(query);
      from.accept(fromVisitor);
      return node;
    }
    return node;
  }
}

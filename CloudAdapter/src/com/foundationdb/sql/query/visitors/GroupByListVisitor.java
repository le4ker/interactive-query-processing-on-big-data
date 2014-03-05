/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.GroupByList;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.SQLQuery;

/**
 *
 * @author heraldkllapi
 */
public class GroupByListVisitor extends AbstractVisitor {

  public GroupByListVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof GroupByList) {
      GroupByColumnVisitor groupByVisitor = new GroupByColumnVisitor(query);
      node.accept(groupByVisitor);
    }
    return node;
  }
}

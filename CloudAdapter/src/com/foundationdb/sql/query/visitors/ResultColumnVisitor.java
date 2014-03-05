/**
 * Copyright MaDgIK Group 2010 - 2013.
 */
package com.foundationdb.sql.query.visitors;

import com.foundationdb.sql.StandardException;
import com.foundationdb.sql.parser.AggregateNode;
import com.foundationdb.sql.parser.ColumnReference;
import com.foundationdb.sql.parser.JavaToSQLValueNode;
import com.foundationdb.sql.parser.JavaValueNode;
import com.foundationdb.sql.parser.ResultColumn;
import com.foundationdb.sql.parser.SQLToJavaValueNode;
import com.foundationdb.sql.parser.StaticMethodCallNode;
import com.foundationdb.sql.parser.Visitable;
import com.foundationdb.sql.query.Column;
import com.foundationdb.sql.query.OutputColumn;
import com.foundationdb.sql.query.OutputFunction;
import com.foundationdb.sql.query.SQLQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author heraldkllapi
 */
public class ResultColumnVisitor extends AbstractVisitor {
  private static final Logger log = Logger.getLogger(ResultColumnVisitor.class);

  public ResultColumnVisitor(SQLQuery query) {
    super(query);
  }

  @Override
  public Visitable visit(Visitable node) throws StandardException {
    if (node instanceof ResultColumn) {
      ResultColumn parserColumn = (ResultColumn)node;
      // Simple output column
      if (parserColumn.getExpression() instanceof ColumnReference) {
        OutputColumn queryColumn = new OutputColumn();
        ColumnReference columnRef = (ColumnReference)parserColumn.getExpression();
        queryColumn.outputName = parserColumn.getName();
        queryColumn.column.tableAlias = columnRef.getTableName();
        queryColumn.column.columnName = columnRef.getColumnName();
        query.outputColumns.add(queryColumn);
      }
      // Function output column
      if (parserColumn.getExpression() instanceof AggregateNode) {
        AggregateNode call = (AggregateNode)parserColumn.getExpression();
        OutputFunction func = new OutputFunction();
        func.functionName = call.getAggregateName().toLowerCase();
        func.outputName = parserColumn.getName();
        // Param
        Column column = new Column();
        ColumnReference col = (ColumnReference)call.getOperand();
        column.tableAlias = col.getTableName();
        column.columnName = col.getColumnName();
        func.params.add(column);
        query.outputFunctions.add(func);
      }
      if (parserColumn.getExpression() instanceof JavaToSQLValueNode) {
        OutputFunction func = new OutputFunction();
        StaticMethodCallNode call = (StaticMethodCallNode)((JavaToSQLValueNode)parserColumn.getExpression()).getJavaValueNode();
        func.functionName = call.getMethodName().toLowerCase();
        // Params
        for (JavaValueNode param : call.getMethodParameters()) {
          SQLToJavaValueNode jNode = (SQLToJavaValueNode)param;
          Column column = new Column();
          ColumnReference col = (ColumnReference)jNode.getSQLValueNode();
          column.tableAlias = col.getTableName();
          column.columnName = col.getColumnName();
          func.params.add(column);
        }
        func.outputName = parserColumn.getName();
        query.outputFunctions.add(func);
      }

      return node;
    }
    log.trace("Skip: " + node.getClass().getSimpleName());
    return node;
  }
}

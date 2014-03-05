/**
 * Copyright 2011-2013 FoundationDB, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.impl.sql.compile.C_NodeNames

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.foundationdb.sql.parser;

/**
 * This is the set of constants used to identify the classes
 * that are used in NodeFactoryImpl.
 *
 * This class is not shipped. The names are used in
 * NodeFactoryImpl, mapped from int NodeTypes and used in
 * Class.forName calls.
 *
 * WARNING: WHEN ADDING NODE TYPES HERE, YOU MUST ALSO ADD
 * THEM TO tools/jar/DBMSnodes.properties
 *
 */

public interface NodeNames
{

    // The names are in alphabetic order.

    static final String AGGREGATE_NODE_NAME = "com.foundationdb.sql.parser.AggregateNode";

    static final String AGGREGATE_WINDOW_FUNCTION_NAME = "com.foundationdb.sql.parser.AggregateWindowFunctionNode";

    static final String INDEX_CONSTRAINT_NAME = "com.foundationdb.sql.parser.IndexConstraintDefinitionNode";

    static final String ALL_RESULT_COLUMN_NAME = "com.foundationdb.sql.parser.AllResultColumn";

    static final String ALTER_SERVER_NODE_NAME = "com.foundationdb.sql.parser.AlterServerNode";
    
    static final String ALTER_TABLE_NODE_NAME = "com.foundationdb.sql.parser.AlterTableNode";
    
    static final String AT_DROP_INDEX_NODE_NAME = "com.foundationdb.sql.parser.AlterDropIndexNode";
    
    static final String AT_ADD_INDEX_NODE_NAME = "com.foundationdb.sql.parser.AlterAddIndexNode";

    static final String AT_RENAME_NODE_NAME = "com.foundationdb.sql.parser.AlterTableRenameNode";

    static final String AT_RENAME_COLUMN_NODE_NAME = "com.foundationdb.sql.parser.AlterTableRenameColumnNode";
    
    static final String AND_NODE_NAME = "com.foundationdb.sql.parser.AndNode";

    static final String BASE_COLUMN_NODE_NAME = "com.foundationdb.sql.parser.BaseColumnNode";

    static final String BETWEEN_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.BetweenOperatorNode";

    static final String BINARY_ARITHMETIC_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.BinaryArithmeticOperatorNode";

    static final String BINARY_BIT_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.BinaryBitOperatorNode";

    static final String BINARY_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.BinaryOperatorNode";

    static final String BINARY_RELATIONAL_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.BinaryRelationalOperatorNode";

    static final String LEFT_RIGHT_FUNC_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.LeftRightFuncOperatorNode";
    
    static final String ROW_CTOR_NODE_NAME = "com.foundationdb.sql.parser.RowConstructorNode";

    static final String BIT_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.BitConstantNode";

    static final String BOOLEAN_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.BooleanConstantNode";

    static final String CALL_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.CallStatementNode";

    static final String CAST_NODE_NAME = "com.foundationdb.sql.parser.CastNode";

    static final String CHAR_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.CharConstantNode";

    static final String CLOSE_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.CloseStatementNode";

    static final String COALESCE_FUNCTION_NODE_NAME = "com.foundationdb.sql.parser.CoalesceFunctionNode";

    static final String COLUMN_DEFINITION_NODE_NAME = "com.foundationdb.sql.parser.ColumnDefinitionNode";

    static final String COLUMN_REFERENCE_NAME = "com.foundationdb.sql.parser.ColumnReference";

    static final String CONCATENATION_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.ConcatenationOperatorNode";

    static final String CONDITIONAL_NODE_NAME = "com.foundationdb.sql.parser.ConditionalNode";

    static final String CONSTRAINT_DEFINITION_NODE_NAME = "com.foundationdb.sql.parser.ConstraintDefinitionNode";

    static final String COPY_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.CopyStatementNode";

    static final String CREATE_ALIAS_NODE_NAME = "com.foundationdb.sql.parser.CreateAliasNode";

    static final String CREATE_INDEX_NODE_NAME = "com.foundationdb.sql.parser.CreateIndexNode";

    static final String CREATE_ROLE_NODE_NAME = "com.foundationdb.sql.parser.CreateRoleNode";

    static final String CREATE_SCHEMA_NODE_NAME = "com.foundationdb.sql.parser.CreateSchemaNode";

    static final String CREATE_SEQUENCE_NODE_NAME = "com.foundationdb.sql.parser.CreateSequenceNode";

    static final String CREATE_TABLE_NODE_NAME = "com.foundationdb.sql.parser.CreateTableNode";

    static final String CREATE_TRIGGER_NODE_NAME = "com.foundationdb.sql.parser.CreateTriggerNode";

    static final String CREATE_VIEW_NODE_NAME = "com.foundationdb.sql.parser.CreateViewNode";

    static final String CURRENT_DATETIME_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.CurrentDatetimeOperatorNode";

    static final String CURRENT_OF_NODE_NAME = "com.foundationdb.sql.parser.CurrentOfNode";

    static final String CURRENT_ROW_LOCATION_NODE_NAME = "com.foundationdb.sql.parser.CurrentRowLocationNode";

    static final String CURSOR_NODE_NAME = "com.foundationdb.sql.parser.CursorNode";

    static final String OCTET_LENGTH_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.OctetLengthOperatorNode";

    static final String DEALLOCATE_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.DeallocateStatementNode";

    static final String DECLARE_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.DeclareStatementNode";

    static final String DEFAULT_NODE_NAME = "com.foundationdb.sql.parser.DefaultNode";

    static final String DELETE_NODE_NAME = "com.foundationdb.sql.parser.DeleteNode";

    static final String DISTINCT_NODE_NAME = "com.foundationdb.sql.parser.DistinctNode";

    static final String DML_MOD_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.DMLModStatementNode";

    static final String DROP_ALIAS_NODE_NAME = "com.foundationdb.sql.parser.DropAliasNode";

    static final String DROP_INDEX_NODE_NAME = "com.foundationdb.sql.parser.DropIndexNode";
    
    static final String DROP_GROUP_NODE_NAME = "com.foundationdb.sql.parser.DropGroupNode";

    static final String DROP_ROLE_NODE_NAME = "com.foundationdb.sql.parser.DropRoleNode";

    static final String DROP_SCHEMA_NODE_NAME = "com.foundationdb.sql.parser.DropSchemaNode";

    static final String DROP_SEQUENCE_NODE_NAME = "com.foundationdb.sql.parser.DropSequenceNode";

    static final String DROP_TABLE_NODE_NAME = "com.foundationdb.sql.parser.DropTableNode";

    static final String DROP_TRIGGER_NODE_NAME = "com.foundationdb.sql.parser.DropTriggerNode";

    static final String DROP_VIEW_NODE_NAME = "com.foundationdb.sql.parser.DropViewNode";

    static final String EXECUTE_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.ExecuteStatementNode";

    static final String EXPLAIN_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.ExplainStatementNode";

    static final String EXPLICIT_COLLATE_NODE_NAME = "com.foundationdb.sql.parser.ExplicitCollateNode";

    static final String EXTRACT_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.ExtractOperatorNode";

    static final String FETCH_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.FetchStatementNode";

    static final String FK_CONSTRAINT_DEFINITION_NODE_NAME = "com.foundationdb.sql.parser.FKConstraintDefinitionNode";

    static final String FROM_BASE_TABLE_NAME = "com.foundationdb.sql.parser.FromBaseTable";

    static final String FROM_LIST_NAME = "com.foundationdb.sql.parser.FromList";

    static final String FROM_SUBQUERY_NAME = "com.foundationdb.sql.parser.FromSubquery";

    static final String FROM_VTI_NAME = "com.foundationdb.sql.parser.FromVTI";

    static final String FULL_OUTER_JOIN_NODE_NAME = "com.foundationdb.sql.parser.FullOuterJoinNode";

    static final String GENERATION_CLAUSE_NODE_NAME = "com.foundationdb.sql.parser.GenerationClauseNode";

    static final String GET_CURRENT_CONNECTION_NODE_NAME = "com.foundationdb.sql.parser.GetCurrentConnectionNode";

    static final String GRANT_NODE_NAME = "com.foundationdb.sql.parser.GrantNode";

    static final String GRANT_ROLE_NODE_NAME = "com.foundationdb.sql.parser.GrantRoleNode";

    static final String GROUP_BY_COLUMN_NAME = "com.foundationdb.sql.parser.GroupByColumn";

    static final String GROUP_BY_LIST_NAME = "com.foundationdb.sql.parser.GroupByList";

    static final String GROUP_BY_NODE_NAME = "com.foundationdb.sql.parser.GroupByNode";

    static final String GROUP_CONCAT_NODE_NAME = "com.foundationdb.sql.parser.GroupConcatNode";

    static final String HALF_OUTER_JOIN_NODE_NAME = "com.foundationdb.sql.parser.HalfOuterJoinNode";

    static final String HASH_TABLE_NODE_NAME = "com.foundationdb.sql.parser.HashTableNode";

    static final String INDEX_COLUMN_NAME = "com.foundationdb.sql.parser.IndexColumn";

    static final String INDEX_COLUMN_LIST_NAME = "com.foundationdb.sql.parser.IndexColumnList";

    static final String INDEX_HINT_NODE_NAME = "com.foundationdb.sql.parser.IndexHintNode";

    static final String INDEX_HINT_LIST_NAME = "com.foundationdb.sql.parser.IndexHintList";

    static final String INDEX_TO_BASE_ROW_NODE_NAME = "com.foundationdb.sql.parser.IndexToBaseRowNode";

    static final String INSERT_NODE_NAME = "com.foundationdb.sql.parser.InsertNode";

    static final String INTERSECT_OR_EXCEPT_NODE_NAME = "com.foundationdb.sql.parser.IntersectOrExceptNode";

    static final String IN_LIST_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.InListOperatorNode";

    static final String IS_NODE_NAME = "com.foundationdb.sql.parser.IsNode";

    static final String IS_NULL_NODE_NAME = "com.foundationdb.sql.parser.IsNullNode";

    static final String JAVA_TO_SQL_VALUE_NODE_NAME = "com.foundationdb.sql.parser.JavaToSQLValueNode";

    static final String JOIN_NODE_NAME = "com.foundationdb.sql.parser.JoinNode";

    static final String LENGTH_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.LengthOperatorNode";

    static final String LIKE_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.LikeEscapeOperatorNode";

    static final String LOCK_TABLE_NODE_NAME = "com.foundationdb.sql.parser.LockTableNode";

    static final String MATERIALIZE_RESULT_SET_NODE_NAME = "com.foundationdb.sql.parser.MaterializeResultSetNode";

    static final String MODIFY_COLUMN_NODE_NAME = "com.foundationdb.sql.parser.ModifyColumnNode";

    static final String NEW_INVOCATION_NODE_NAME = "com.foundationdb.sql.parser.NewInvocationNode";

    static final String NEXT_SEQUENCE_NODE_NAME = "com.foundationdb.sql.parser.NextSequenceNode";

    static final String CURRENT_SEQUENCE_NODE_NAME = "com.foundationdb.sql.parser.CurrentSequenceNode";

    static final String NON_STATIC_METHOD_CALL_NODE_NAME = "com.foundationdb.sql.parser.NonStaticMethodCallNode";

    static final String NOP_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.NOPStatementNode";

    static final String NORMALIZE_RESULT_SET_NODE_NAME = "com.foundationdb.sql.parser.NormalizeResultSetNode";

    static final String NOT_NODE_NAME = "com.foundationdb.sql.parser.NotNode";

    static final String NUMERIC_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.NumericConstantNode";

    static final String OR_NODE_NAME = "com.foundationdb.sql.parser.OrNode";

    static final String ORDER_BY_COLUMN_NAME = "com.foundationdb.sql.parser.OrderByColumn";

    static final String ORDER_BY_LIST_NAME = "com.foundationdb.sql.parser.OrderByList";

    static final String ORDER_BY_NODE_NAME = "com.foundationdb.sql.parser.OrderByNode";

    static final String PARAMETER_NODE_NAME = "com.foundationdb.sql.parser.ParameterNode";

    static final String PARTITION_BY_COLUMN_NAME = "com.foundationdb.sql.parser.PartitionByColumn";

    static final String PARTITION_BY_LIST_NAME = "com.foundationdb.sql.parser.PartitionByList";

    static final String PREDICATE_LIST_NAME = "com.foundationdb.sql.parser.PredicateList";

    static final String PREDICATE_NAME = "com.foundationdb.sql.parser.Predicate";

    static final String PREPARE_STATEMENT_NODE_NAME = "com.foundationdb.sql.parser.PrepareStatementNode";

    static final String PRIVILEGE_NAME = "com.foundationdb.sql.parser.PrivilegeNode";

    static final String PROJECT_RESTRICT_NODE_NAME = "com.foundationdb.sql.parser.ProjectRestrictNode";

    static final String RENAME_NODE_NAME = "com.foundationdb.sql.parser.RenameNode";

    static final String RESULT_COLUMN_LIST_NAME = "com.foundationdb.sql.parser.ResultColumnList";

    static final String RESULT_COLUMN_NAME = "com.foundationdb.sql.parser.ResultColumn";

    static final String REVOKE_NODE_NAME = "com.foundationdb.sql.parser.RevokeNode";

    static final String REVOKE_ROLE_NODE_NAME = "com.foundationdb.sql.parser.RevokeRoleNode";

    static final String ROW_COUNT_NODE_NAME = "com.foundationdb.sql.parser.RowCountNode";

    static final String ROW_NUMBER_FUNCTION_NAME = "com.foundationdb.sql.parser.RowNumberFunctionNode";

    static final String ROW_RESULT_SET_NODE_NAME = "com.foundationdb.sql.parser.RowResultSetNode";

    static final String ROWS_RESULT_SET_NODE_NAME = "com.foundationdb.sql.parser.RowsResultSetNode";

    static final String SAVEPOINT_NODE_NAME = "com.foundationdb.sql.parser.SavepointNode";

    static final String SCROLL_INSENSITIVE_RESULT_SET_NODE_NAME = "com.foundationdb.sql.parser.ScrollInsensitiveResultSetNode";

    static final String SELECT_NODE_NAME = "com.foundationdb.sql.parser.SelectNode";

    static final String SET_CONFIGURATION_NODE_NAME = "com.foundationdb.sql.parser.SetConfigurationNode";

    static final String SET_ROLE_NODE_NAME = "com.foundationdb.sql.parser.SetRoleNode";

    static final String SET_SCHEMA_NODE_NAME = "com.foundationdb.sql.parser.SetSchemaNode";

    static final String SET_TRANSACTION_ACCESS_NODE_NAME = "com.foundationdb.sql.parser.SetTransactionAccessNode";

    static final String SET_TRANSACTION_ISOLATION_NODE_NAME = "com.foundationdb.sql.parser.SetTransactionIsolationNode";

    static final String SHOW_CONFIGURATION_NODE_NAME = "com.foundationdb.sql.parser.ShowConfigurationNode";

    static final String SIMPLE_CASE_NODE_NAME = "com.foundationdb.sql.parser.SimpleCaseNode";

    static final String SIMPLE_STRING_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.SimpleStringOperatorNode";

    static final String SPECIAL_FUNCTION_NODE_NAME = "com.foundationdb.sql.parser.SpecialFunctionNode";

    static final String SQL_BOOLEAN_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.SQLBooleanConstantNode";

    static final String SQL_TO_JAVA_VALUE_NODE_NAME = "com.foundationdb.sql.parser.SQLToJavaValueNode";

    static final String STATIC_CLASS_FIELD_REFERENCE_NODE_NAME = "com.foundationdb.sql.parser.StaticClassFieldReferenceNode";

    static final String STATIC_METHOD_CALL_NODE_NAME = "com.foundationdb.sql.parser.StaticMethodCallNode";

    static final String STORAGE_FORMAT_NODE_NAME = "com.foundationdb.sql.parser.StorageFormatNode";

    static final String SUBQUERY_LIST_NAME = "com.foundationdb.sql.parser.SubqueryList";

    static final String SUBQUERY_NODE_NAME = "com.foundationdb.sql.parser.SubqueryNode";

    static final String TABLE_ELEMENT_LIST_NAME = "com.foundationdb.sql.parser.TableElementList";

    static final String TABLE_ELEMENT_NODE_NAME = "com.foundationdb.sql.parser.TableElementNode";

    static final String TABLE_NAME_NAME = "com.foundationdb.sql.parser.TableName";

    static final String TABLE_PRIVILEGES_NAME = "com.foundationdb.sql.parser.TablePrivilegesNode";

    static final String TERNARY_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.TernaryOperatorNode";

    static final String TEST_CONSTRAINT_NODE_NAME = "com.foundationdb.sql.parser.TestConstraintNode";

    static final String TIMESTAMP_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.TimestampOperatorNode";

    static final String TRANSACTION_CONTROL_NODE_NAME = "com.foundationdb.sql.parser.TransactionControlNode";

    static final String TRIM_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.TrimOperatorNode";
    
    static final String UNARY_ARITHMETIC_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.UnaryArithmeticOperatorNode";

    static final String UNARY_BIT_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.UnaryBitOperatorNode";

    static final String UNARY_DATE_TIMESTAMP_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.UnaryDateTimestampOperatorNode";

    static final String UNARY_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.UnaryOperatorNode";

    static final String UNION_NODE_NAME = "com.foundationdb.sql.parser.UnionNode";

    static final String UNTYPED_NULL_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.UntypedNullConstantNode";

    static final String UPDATE_NODE_NAME = "com.foundationdb.sql.parser.UpdateNode";

    static final String USERTYPE_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.UserTypeConstantNode";

    static final String VALUE_NODE_LIST_NAME = "com.foundationdb.sql.parser.ValueNodeList";

    static final String VARBIT_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.VarbitConstantNode";

    static final String VIRTUAL_COLUMN_NODE_NAME = "com.foundationdb.sql.parser.VirtualColumnNode";

    static final String WINDOW_DEFINITION_NAME = "com.foundationdb.sql.parser.WindowDefinitionNode";

    static final String WINDOW_REFERENCE_NAME = "com.foundationdb.sql.parser.WindowReferenceNode";

    static final String WINDOW_RESULTSET_NODE_NAME = "com.foundationdb.sql.parser.WindowResultSetNode";

    static final String XML_BINARY_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.XMLBinaryOperatorNode";

    static final String XML_CONSTANT_NODE_NAME = "com.foundationdb.sql.parser.XMLConstantNode";

    static final String XML_UNARY_OPERATOR_NODE_NAME = "com.foundationdb.sql.parser.XMLUnaryOperatorNode";

    // The names are in alphabetic order.

}

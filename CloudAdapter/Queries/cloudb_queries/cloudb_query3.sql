DISTRIBUTED CREATE TEMPORARY TABLE leaf as
SELECT  o.o_orderpriority as o_orderpriority, count( l.l_orderkey) as count_1
FROM  lineitem l, orders o
WHERE  o.o_orderdate >= '1993-03-01' AND o.o_orderdate < '1993-06-01' AND l.l_orderkey = o.o_orderkey AND l.l_commitdate = l.l_receiptdate
GROUP BY  o_orderpriority
ORDER BY  o_orderpriority;

DISTRIBUTED CREATE TEMPORARY TABLE internal as
SELECT  o_orderpriority as o_orderpriority, sum( count_1) as count_2
FROM  leaf
GROUP BY  o_orderpriority
ORDER BY  o_orderpriority;

DISTRIBUTED CREATE TEMPORARY TABLE root to 1 as
SELECT  o_orderpriority as o_orderpriority, sum( count_2) as order_count
FROM  internal
GROUP BY  o_orderpriority
ORDER BY  o_orderpriority;
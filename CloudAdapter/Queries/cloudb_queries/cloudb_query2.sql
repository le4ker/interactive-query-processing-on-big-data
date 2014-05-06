DISTRIBUTED CREATE TEMPORARY TABLE leaf as
SELECT  l.l_orderkey as l_orderkey, l.l_discount as l_discount, o.o_orderdate as o_orderdate, o.o_shippriority as o_shippriority, sum( l.l_extendedprice) as sum_1
FROM  orders o, customer c, lineitem l
WHERE  c.c_mktsegment = 'BUILDING' AND o.o_orderdate < '1995-03-15' AND l.l_shipdate > '1995-03-15' AND c.c_custkey = o.o_custkey AND l.l_orderkey = o.o_orderkey
GROUP BY  l_orderkey, o_orderdate, o_shippriority
ORDER BY  sum_1, o_orderdate LIMIT  10;

DISTRIBUTED CREATE TEMPORARY TABLE internal as
SELECT  l_orderkey as l_orderkey, l_discount as l_discount, o_orderdate as o_orderdate, o_shippriority as o_shippriority, sum( sum_1) as sum_2
FROM  leaf
GROUP BY  l_orderkey, o_orderdate, o_shippriority
ORDER BY  sum_2, o_orderdate
LIMIT  10;

DISTRIBUTED CREATE TEMPORARY TABLE root to 1 as
SELECT  l_orderkey as l_orderkey, l_discount as l_discount, o_orderdate as o_orderdate, o_shippriority as o_shippriority, sum( sum_2) as revenue
FROM  internal
GROUP BY  l_orderkey, o_orderdate, o_shippriority
ORDER BY  revenue, o_orderdate
LIMIT  10;

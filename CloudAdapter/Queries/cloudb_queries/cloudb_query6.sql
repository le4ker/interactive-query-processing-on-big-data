DISTRIBUTED CREATE TEMPORARY TABLE leaf as
SELECT  n.n_name as nation, o.o_orderdate as o_year, sum( l.l_extendedprice) as sum_1
FROM  supplier s, partsupp ps, nation n, lineitem l, part p, orders o
WHERE  s.s_suppkey = l.l_suppkey AND ps.ps_suppkey = l.l_suppkey AND ps.ps_partkey = l.l_partkey AND p.p_partkey = l.l_partkey AND o.o_orderkey = l.l_orderkey AND s.s_nationkey = n.n_nationkey
GROUP BY  nation, o_year
ORDER BY  nation, o_year;

DISTRIBUTED CREATE TEMPORARY TABLE internal as
SELECT  nation as nation, o_year as o_year, sum( sum_1) as sum_2
FROM  leaf
GROUP BY  nation, o_year
ORDER BY  nation, o_year;

DISTRIBUTED CREATE TEMPORARY TABLE root to 1 as
SELECT  nation as nation, o_year as o_year, sum( sum_2) as sum_profit
FROM  internal
GROUP BY  nation, o_year
ORDER BY  nation, o_year;
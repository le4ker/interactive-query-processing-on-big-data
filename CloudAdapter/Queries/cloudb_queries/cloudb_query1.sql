DISTRIBUTED CREATE TEMPORARY TABLE leaf as
SELECT  l.l_returnflag as l_returnflag, l.l_linestatus as l_linestatus, sum( l.l_discount) as sum_1, count( l.l_discount) as count_1, count( l.l_orderkey) as count_3 
FROM  lineitem l 
WHERE  l.l_shipdate <= '1998-12-01' 
GROUP BY  l_returnflag, l_linestatus 
ORDER BY  l_returnflag, l_linestatus;

DISTRIBUTED CREATE TEMPORARY TABLE internal as
SELECT  l_returnflag as l_returnflag, l_linestatus as l_linestatus, sum( sum_1) as sum_2, sum( count_1) as count_2, sum( count_3) as count_4 
FROM  leaf 
GROUP BY  l_returnflag, l_linestatus 
ORDER BY  l_returnflag, l_linestatus;

DISTRIBUTED CREATE TEMPORARY TABLE root to 1 as
SELECT  l_returnflag as l_returnflag, l_linestatus as l_linestatus, sum( sum_2) as avg_disc, sum( count_2) as avg_disc, sum( count_4) as count_order 
FROM  internal 
GROUP BY  l_returnflag, l_linestatus 
ORDER BY  l_returnflag, l_linestatus;

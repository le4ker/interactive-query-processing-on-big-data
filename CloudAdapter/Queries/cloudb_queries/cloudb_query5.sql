DISTRIBUTED CREATE TEMPORARY TABLE leaf as
SELECT  n1.n_name as supp_nation, n2.n_name as cust_nation, l_shipdate as l_year, sum( l_extendedprice) as sum_1
FROM  supplier s, lineitem l, orders o, customer c, nation n1, nation n2
WHERE  n1.n_name = 'VIETNAM' AND n2.n_name = 'IRAN' AND l.l_shipdate > '1995-01-01' AND l.l_shipdate < '1996-12-31' AND s.s_suppkey = l.l_suppkey AND o.o_orderkey = l.l_orderkey AND c.c_custkey = o.o_custkey AND s.s_nationkey = n1.n_nationkey AND c.c_nationkey = n2.n_nationkey
GROUP BY  supp_nation, cust_nation, l_year
ORDER BY  supp_nation, cust_nation, l_year;

DISTRIBUTED CREATE TEMPORARY TABLE internal as
SELECT  supp_nation as supp_nation, cust_nation as cust_nation, l_year as l_year, sum( sum_1) as sum_2
FROM  leaf
GROUP BY  supp_nation, cust_nation, l_year
ORDER BY  supp_nation, cust_nation, l_year;

DISTRIBUTED CREATE TEMPORARY TABLE root to 1 as
SELECT  supp_nation as supp_nation, cust_nation as cust_nation, l_year as l_year, sum( sum_2) as revenue
FROM  internal
GROUP BY  supp_nation, cust_nation, l_year
ORDER BY  supp_nation, cust_nation, l_year;
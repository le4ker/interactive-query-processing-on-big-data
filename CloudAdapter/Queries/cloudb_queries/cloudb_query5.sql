SELECT  internal.supp_nation, internal.cust_nation, internal.l_year, sum( sum_2) as revenue 
FROM
(
	SELECT  leaf.supp_nation, leaf.cust_nation, leaf.l_year, sum( sum_1) as sum_2 
	FROM
	(
		SELECT  n1.n_name as supp_nation, n2.n_name as cust_nation, l_shipdate as l_year, sum( l_extendedprice) as sum_1 
		FROM  lineitem l, customer c, supplier s, nation n1, nation n2, orders o 
		WHERE  n1.n_name = 'VIETNAM' AND n2.n_name = 'IRAN' AND l.l_shipdate > '1995-01-01' AND l.l_shipdate < '1996-12-31' AND s.s_suppkey = l.l_suppkey AND o.o_orderkey = l.l_orderkey AND c.c_custkey = o.o_custkey AND s.s_nationkey = n1.n_nationkey AND c.c_nationkey = n2.n_nationkey 
		GROUP BY  supp_nation, cust_nation, l_year 
		ORDER BY  supp_nation, cust_nation, l_year
		) as leaf  
	GROUP BY  leaf.supp_nation, leaf.cust_nation, leaf.l_year 
	ORDER BY  leaf.supp_nation, leaf.cust_nation, leaf.l_year
) as internal 
GROUP BY  internal.supp_nation, internal.cust_nation, internal.l_year 
ORDER BY  internal.supp_nation, internal.cust_nation, internal.l_year;
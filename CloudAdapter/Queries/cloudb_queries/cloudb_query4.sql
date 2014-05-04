SELECT  internal.l_discount, internal.n_name, sum( sum_2) as revenue 
FROM
(
	SELECT  leaf.l_discount, leaf.n_name, sum( sum_1) as sum_2 
	FROM
	(
		SELECT  l.l_discount, n.n_name, sum( l.l_extendedprice) as sum_1 
		FROM  orders o, supplier s, region r, lineitem l, customer c, nation n 
		WHERE  r.r_name = 'MIDDLE EAST' AND o.o_orderdate >= '1994-01-01' AND o.o_orderdate < '1995-01-01' AND c.c_custkey = o.o_custkey AND l.l_orderkey = o.o_orderkey AND l.l_suppkey = s.s_suppkey AND c.c_nationkey = s.s_nationkey AND s.s_nationkey = n.n_nationkey AND n.n_regionkey = r.r_regionkey 
		GROUP BY  n_name 
		ORDER BY  sum_1
	) as leaf  
	GROUP BY  leaf.n_name 
	ORDER BY  sum_2
	) as internal 
GROUP BY  n_name 
ORDER BY  revenue desc;

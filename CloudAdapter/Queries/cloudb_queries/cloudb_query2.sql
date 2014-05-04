SELECT  internal.l_orderkey, internal.l_discount, internal.o_orderdate, internal.o_shippriority, sum( sum_2) as revenue 
FROM  
	(
		SELECT  leaf.l_orderkey, leaf.l_discount, leaf.o_orderdate, leaf.o_shippriority, sum( sum_1) as sum_2 
		FROM
		(
			SELECT  l.l_orderkey, l.l_discount, o.o_orderdate, o.o_shippriority, sum( l.l_extendedprice) as sum_1 
			FROM  orders o, customer c, lineitem l 
			WHERE  c.c_mktsegment = 'BUILDING' AND o.o_orderdate < '1995-03-15' AND l.l_shipdate > '1995-03-15' AND c.c_custkey = o.o_custkey AND l.l_orderkey = o.o_orderkey 
			GROUP BY  l_orderkey, o_orderdate, o_shippriority 
			ORDER BY  sum_1 desc, o_orderdate 
			LIMIT  10
		) as leaf
		GROUP BY  leaf.l_orderkey, leaf.o_orderdate, leaf.o_shippriority 
		ORDER BY  sum_2 desc, leaf.o_orderdate 
		LIMIT  10
	) as internal
GROUP BY  internal.l_orderkey, internal.o_orderdate, internal.o_shippriority 
ORDER BY  revenue desc, internal.o_orderdate 
LIMIT  10; 
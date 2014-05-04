SELECT  internal.o_orderpriority, sum( count_2) as order_count 
FROM  
	(
		SELECT  leaf.o_orderpriority, sum( count_1) as count_2 
		FROM
		(
			SELECT  o.o_orderpriority, count( distinct l.l_orderkey) as count_1 
			FROM  lineitem l, orders o 
			WHERE  o.o_orderdate >= '1993-03-01' AND o.o_orderdate < '1993-06-01' AND l.l_orderkey = o.o_orderkey AND l.l_commitdate < l.l_receiptdate 
			GROUP BY  o.o_orderpriority 
			ORDER BY  o.o_orderpriority	
		) as leaf  
		GROUP BY  leaf.o_orderpriority 
		ORDER BY  leaf.o_orderpriority
	) as internal 
GROUP BY  internal.o_orderpriority
ORDER BY  internal.o_orderpriority; 
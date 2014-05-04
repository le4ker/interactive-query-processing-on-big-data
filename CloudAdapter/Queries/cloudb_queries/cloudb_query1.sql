SELECT  internal.l_returnflag, internal.l_linestatus, sum( count_2) as avg_disc, sum( sum_2) as avg_disc, sum( count_4) as count_order 
FROM  
	(
		SELECT  leaf.l_returnflag, leaf.l_linestatus, sum( count_1) as count_2, sum( sum_1) as sum_2, sum( count_3) as count_4 
		FROM  
			(
				SELECT  l.l_returnflag, l.l_linestatus, count( l.l_discount) as count_1, sum( l.l_discount) as sum_1, count( l.l_orderkey) as count_3 
				FROM  lineitem l 
				WHERE  l.l_shipdate <= '1998-12-01' 
				GROUP BY  l.l_returnflag, l.l_linestatus 
				ORDER BY  l.l_returnflag, l.l_linestatus
			) as leaf
		GROUP BY  leaf.l_returnflag, leaf.l_linestatus 
		ORDER BY  leaf.l_returnflag, leaf.l_linestatus
	) as internal
GROUP BY  internal.l_returnflag, internal.l_linestatus 
ORDER BY  internal.l_returnflag, internal.l_linestatus;
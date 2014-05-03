distributed create table nation as external
select
  cast(c1 as int) as n_nationkey,
  cast(c2 as text) as n_name,
  cast(c3 as int) as n_regionkey,
  cast(c4 as text) as n_comment
from (file '/home/adp/data/nation.tbl.gz' delimiter:|);

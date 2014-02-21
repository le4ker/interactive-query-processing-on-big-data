package test;

import com.foundationdb.sql.parser.SQLParserException;
import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.SQLQueryParser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by panossakkos on 2/15/14.
 *
 * The following class must contain only test methods.
 */

public class ParseTests {

    public static boolean query1 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                    " select l.l_returnflag, l.l_linestatus, " +
                    " avg(l.l_discount) as avg_disc, " +
                    " count(l.l_orderkey) as count_order " +
                    " from lineitem l" +
                    " where l.l_shipdate <= '1998-12-01' " +
                    " group by l.l_returnflag, l.l_linestatus " +
                    " order by l.l_returnflag, l.l_linestatus "
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query2 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                    " select l.l_orderkey, l.l_discount, " +
                    " sum(l.l_extendedprice) as revenue, " +
                    " o.o_orderdate, " +
                    " o.o_shippriority " +
                    " from customer c, orders o, lineitem l " +
                    " where " +
                    "    c.c_custkey = o.o_custkey and " +
                    "    l.l_orderkey = o.o_orderkey and  " +
                    "    c.c_mktsegment = 'BUILDING' and " +
                    "    o.o_orderdate < '1995-03-15' and " +
                    "    l.l_shipdate > '1995-03-15' " +
                    " group by l.l_orderkey, o.o_orderdate, o.o_shippriority " +
                    " order by revenue desc, o.o_orderdate " +
                    " limit 10 "
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query3 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                    " select o.o_orderpriority, " +
                    " count(o.o_orderkey) as order_count " +
                    " from orders o " +
                    " where " +
                    " o.o_orderdate >= '1993-03-01' " +
                    " and o.o_orderdate < '1993-06-01' " +
                    " and exists " +
                    " (" +
                    "  select l.l_orderkey" +
                    "  from lineitem l" +
                    "  where l.l_orderkey = o.o_orderkey " +
                    "  and l.l_commitdate < l.l_receiptdate " +
                    " )" +
                    " group by o.o_orderpriority " +
                    " order by o.o_orderpriority "
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query4 () throws Exception{
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                    "select l.l_discount," +
                    " n.n_name, " +
                    " sum(l.l_extendedprice) as revenue " +
                    " from customer c, orders o, lineitem l, supplier s, nation n, region r " +
                    " where " +
                    "     c.c_custkey = o.o_custkey " +
                    "     and l.l_orderkey = o.o_orderkey " +
                    "     and l.l_suppkey = s.s_suppkey " +
                    "     and c.c_nationkey = s.s_nationkey " +
                    "     and s.s_nationkey = n.n_nationkey " +
                    "     and n.n_regionkey = r.r_regionkey " +
                    "     and r.r_name = 'MIDDLE EAST' " +
                    "     and o.o_orderdate >= '1994-01-01' " +
                    "     and o.o_orderdate < '1995-01-01' " +
                    " group by n.n_name " +
                    " order by revenue desc "
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query5 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                " select " +
                "  s.supp_nation, " +
                "  c.cust_nation, " +
                "  l.l_year, " +
                "  sum(volume) as revenue " +
                " from " +
                "  ( " +
                "    select " +
                "      n1.n_name as supp_nation, " +
                "      n2.n_name as cust_nation, " +
                "      l.l_shipdate as l_year, " +
                "      l.l_extendedprice * (1 - l.l_discount) as volume " +
                "    from supplier s, lineitem l, orders o, customer c, nation n1, nation n2 " +
                "    where " +
                "      s.s_suppkey = l.l_suppkey " +
                "      and o.o_orderkey = l.l_orderkey " +
                "      and c.c_custkey = o.o_custkey " +
                "      and s.s_nationkey = n1.n_nationkey " +
                "      and c.c_nationkey = n2.n_nationkey " +
                "      and ( " +
                "        (n1.n_name = 'VIETNAM' and n2.n_name = 'IRAN') " +
                "        or (n1.n_name = 'IRAN' and n2.n_name = 'VIETNAM') " +
                "      ) " +
                "      and l.l_shipdate > '1995-01-01' " +
                "      and l.l_shipdate < '1996-12-31' " +
                "  ) as shipping "
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query6 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                " select " +
                " nation," +
                " o_year," +
                " sum(amount) as sum_profit " +
                " from " +
                "  ( " +
                "    select" +
                "      n.n_name as nation," +
                "      o.o_orderdate as o_year," +
                "      l.l_extendedprice * (1 - l.l_discount) - ps.ps_supplycost * l.l_quantity as amount " +
                "    from part p, supplier s, lineitem l, partsupp ps, orders o, nation n" +
                "    where " +
                "              s.s_suppkey = l.l_suppkey " +
                "      and ps.ps_suppkey = l.l_suppkey " +
                "      and ps.ps_partkey = l.l_partkey " +
                "      and p.p_partkey = l.l_partkey " +
                "      and o.o_orderkey = l.l_orderkey " +
                "      and s.s_nationkey = n.n_nationkey " +
                "      and p.p_name like '%sky%' " +
                "  ) as profit " +
                " group by " +
                "  nation, " +
                "  o_year " +
                " order by " +
                "  nation, " +
                "  o_year desc"
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

    public static boolean query7 () throws Exception {
        try {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.OFF);

            SQLQuery query = SQLQueryParser.parse(
                " select " +
                        "  o_year, " +
                        "  sum(case " +
                        "              when nation = 'IRAN' then volume " +
                        "              else 0 " +
                        "           end) / sum(volume) as mkt_share " +
                        " from " +
                        "  (" +
                        "    select " +
                        "      o.o_orderdate o_year, " +
                        "      l.l_extendedprice * (1 - l.l_discount) as volume, " +
                        "      n2.n_name as nation " +
                        "    from part p, supplier s, lineitem l, orders o, customer c, nation n1, nation n2, region r" +
                        "    where " +
                        "              p.p_partkey = l.l_partkey " +
                        "      and l.l_orderkey = o.o_orderkey " +
                        "      and s.s_suppkey = l.l_suppkey " +
                        "      and s.s_nationkey = n2.n_nationkey " +
                        "      and c.c_nationkey = n1.n_nationkey " +
                        "      and n1.n_regionkey = r.r_regionkey " +
                        "      and o.o_custkey = c.c_custkey " +
                        "      and o.o_orderdate > '1995-01-01' " +
                        "      and o.o_orderdate < '1996-12-31' " +
                        "      and r.r_name = 'MIDDLE EAST' " +
                        "      and p.p_type = 'PROMO ANODIZED STEEL' " +
                        "  ) as all_nations " +
                        "group by o_year " +
                        "order by o_year"
            );

            System.out.println(query.toString());
        }
        catch (ClassCastException|SQLParserException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();

            return false;
        }

        return true;
    }

}

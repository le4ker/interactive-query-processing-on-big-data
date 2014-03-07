package di.madgik.cloudb.adapter;

import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.SQLQueryParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Shell {

    private static String QUERIES_FOLDER = "Queries/";

    public static void main (String []args) {

        String command;
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        while ((command = scanner.nextLine()) != null) {

            String queries[] = command.split(" ");

            if (queries[0].compareTo("q") == 0) {
                return;
            }
            else if (queries[0].compareTo("new") == 0) {

                /* Example: new avg sum count */

                if (queries.length < 3) {

                    System.err.println("New rules must follow the format: new <function name> <primitive 1> <primitive 2> ...");
                    continue;
                }

                String newAggregationFunction = queries[1];
                ArrayList<String> primitives = new ArrayList<String>();

                for (int i = 2; i < queries.length; i++) {
                    primitives.add(queries[i]);
                }

                try {
                    Cloudify.addRule(newAggregationFunction, primitives);
                }
                catch (Exception exception) {
                    System.err.println(exception.getMessage());
                }

                continue;
            }
            else {
                for (String query : queries) {
                    cloudifyQueryFromFile(query);
                }
            }
            System.out.print("> ");
        }
    }

    private static void cloudifyQueryFromFile(String fileName) {
        try {
            String content = new Scanner(new File(Shell.QUERIES_FOLDER + fileName)).useDelimiter("\\Z").next();

            SQLQuery query = SQLQueryParser.parse(content);

            CloudQuery cloudQuery = Cloudify.toCloud(query);

            System.out.println(cloudQuery.toShellSQLString());

        }
        catch(Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}

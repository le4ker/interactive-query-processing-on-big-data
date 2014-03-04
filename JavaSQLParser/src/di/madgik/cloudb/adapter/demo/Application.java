package di.madgik.cloudb.adapter.demo;

import com.foundationdb.sql.query.SQLQuery;
import com.foundationdb.sql.query.SQLQueryParser;
import di.madgik.cloudb.adapter.CloudQuery;
import di.madgik.cloudb.adapter.Cloudify;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Created by panossakkos on 2/21/14.
 */

public class Application {

    private static String QUERIES_FOLDER = "Queries/";

    public static void main (String []args) throws Exception {

        String command;
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        while ((command = scanner.nextLine()) != null) {
            String queries[] = command.split(" ");

            for (String query : queries) {

                if (query.compareTo("q") == 0) {
                    return;
                }

                cloudifyQueryFromFile(query);
            }

            System.out.print("> ");
        }
    }

    private static void cloudifyQueryFromFile(String fileName) {
        try {
            String content = new Scanner(new File(Application.QUERIES_FOLDER + fileName)).useDelimiter("\\Z").next();

            SQLQuery query = SQLQueryParser.parse(content);

            CloudQuery cloudQuery = Cloudify.toCloud(query);

            System.out.println(cloudQuery);

        }
        catch(Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}

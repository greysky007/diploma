package data;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

@UtilityClass

public class DataToBase {
    private static String url = System.getProperty("db.url");
    @SneakyThrows
    public static String selection(String table) {
        var runner = new QueryRunner();

        String select = "select status FROM "+table+";";

        try (
                var conn = DriverManager
                        .getConnection(url, "app-line", "12345");

        ) {

            String count = runner.query(conn, select, new ScalarHandler<>());


            return count;

        }
    }



    @SneakyThrows
    public void delInfoFromTables() {
        var runner = new QueryRunner();
        String deleteCodes = "DELETE from credit_request_entity;";
        String deleteCards = "DELETE from order_entity;";
        String deleteUsers = "DELETE from payment_entity;";
        try (
                var conn = DriverManager
                        .getConnection(url, "app-line", "12345");

        ) {
            runner.update(conn, deleteCards);
            runner.update(conn, deleteCodes);
            runner.update(conn, deleteUsers);


        }
    }
}

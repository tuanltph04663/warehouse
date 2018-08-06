package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RZ09
 */
public class DBConn {

    private static Connection conn;
    private static final String CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://DESKTOP-T745RE3\\giaduc:1433;databaseName=QLKHO";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "1";

    public static Connection getconnection() {
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connect successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Can't connect.");
        }

        return conn;
    }

    public static void main(String[] args) {
        DBConn.getconnection();
    }
}

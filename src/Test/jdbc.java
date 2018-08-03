package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RZ09
 */
public class jdbc {

    private static Connection conn;
    private static final String CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://TABI\\TABI:1433;databaseName=QLKHO";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "1";

    public static Connection getconnection() {
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Da ket noi");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Khong the ket noi");
        }

        return conn;
    }

    public static void main(String[] args) {
        jdbc.getconnection();
    }
}

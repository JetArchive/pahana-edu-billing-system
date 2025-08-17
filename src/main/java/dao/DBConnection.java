package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple singleton DB connection helper.
 * Change USER and PASS to suit your MySQL credentials.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/st20284491?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "admin"; // change to your password

    private static Connection conn = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL driver not found", e);
            }
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}

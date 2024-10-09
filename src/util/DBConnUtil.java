package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static Connection connection;

    // Static method to establish and return the connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Reading connection properties from db.properties file
                String connectionString = DBPropertyUtil.getPropertyString("db.properties");
                
                // Establishing connection
                connection = DriverManager.getConnection(connectionString);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Method to close the connection if needed
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

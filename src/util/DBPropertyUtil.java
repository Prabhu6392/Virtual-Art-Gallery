package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileNotFoundException;

public class DBPropertyUtil {
    public static String getPropertyString(String fileName) {
        Properties properties = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            // Check if the file is found
            if (input == null) {
                throw new FileNotFoundException("Property file '" + fileName + "' not found in the classpath");
            }
            // Load properties from the file
            properties.load(input);

            // Constructing the connection string
            String hostname = properties.getProperty("hostname");
            String port = properties.getProperty("port");
            String dbname = properties.getProperty("dbname");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            // Example for MySQL connection string format
            return "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

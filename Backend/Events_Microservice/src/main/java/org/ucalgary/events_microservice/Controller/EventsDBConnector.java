package org.ucalgary.events_microservice.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventsDBConnector {
    
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:mysql://34.66.69.126:3306/events";
        String username = "symasc";
        String password = "20YallaNow24";
        
        // Attempt to connect to the database
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // If connection is successful, print a success message
            System.out.println("Connected to the database successfully!");
            
            // Example query to test database connectivity
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Events LIMIT 1");
                // If query executes successfully, print the result
                while (resultSet.next()) {
                    System.out.println("Example data: " + resultSet.getString("eventID"));
                }
            }
        } catch (SQLException e) {
            // If connection fails, print error message
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}

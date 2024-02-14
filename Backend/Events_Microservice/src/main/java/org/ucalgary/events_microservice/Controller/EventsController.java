package org.ucalgary.events_microservice.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.ucalgary.events_microservice.Entity.*;

public class EventsController {
    private final static String url = "jdbc:mysql://34.66.69.126:3306/events";
    private final static String username = "symasc";
    private final static String password = "20YallaNow24";

    public static void createEvent(Event event) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Events (groupID, eventTitle, eventDescription, location, eventStartTime, eventEndTime, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, event.getGroupID());
                statement.setString(2, event.getEventTitle());
                statement.setString(3, event.getEventDescription());
                statement.setString(4, event.getLocation().toString());
                statement.setString(5, event.getEventStartTime().toString());
                statement.setString(6, event.getEventEndTime().toString());
                statement.setString(7, event.getStatus().toString());
                statement.executeUpdate();

                System.out.println("Event created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error uploading event to the database: " + e.getMessage());
        }
    }

    public void updateEvent() {
        // TODO implement here
    }

    public void deleteEvent() {
        // TODO implement here
    }

    public void suggestEvent() {
        // TODO implement here
    }

    public void retrieveEvent() {
        // TODO implement here
    }

    public void addParticipant(){
        // TODO implement here
    }

    public void removeParticipant(){
        // TODO implement here
    }

    public void updateParticipantStatus(){
        // TODO implement here
    }

    public static void main(String[] args){
        Address testAddress = new Address(123, "Calgary", "Alberta", "Canada");
        Time testStartTime = new Time(12, 0);
        Time testEndTime = new Time(3, 0);

        Event event = new Event(1, "Test Event", "This is a test event", testAddress, testStartTime, testEndTime, EventStatus.Scheduled);

        createEvent(event);
    }
}

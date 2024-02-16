package org.ucalgary.events_microservice.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.ucalgary.events_microservice.DTO.AddressDTO;
import org.ucalgary.events_microservice.DTO.EventDTO;
import org.ucalgary.events_microservice.DTO.EventStatus;
import org.ucalgary.events_microservice.DTO.TimeDTO;


public class EventHandler {
    @Value("${spring.datasource.url}")
    private static String url;

    @Value("${spring.datasource.username}")
    private static String username;

    @Value("${spring.datasource.password}")
    private static String password;    

    public static void createEvent(EventDTO event) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Events (groupID, eventTitle, eventDescription, location, eventDate, eventStartTime, eventEndTime, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, event.getGroupID());
                statement.setString(2, event.getEventTitle());
                statement.setString(3, event.getEventDescription());
                statement.setString(4, event.getLocation().toString());
                statement.setString(5, event.getEventDate().toString());
                statement.setString(6, event.getEventStartTime().toString());
                statement.setString(7, event.getEventEndTime().toString());
                statement.setString(8, event.getStatus().toString());
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
        AddressDTO testAddress = new AddressDTO(123, "Calgary", "Alberta", "Canada");
        TimeDTO testStartTime = new TimeDTO(12, 0);
        TimeDTO testEndTime = new TimeDTO(3, 0);
        LocalDate testDate = LocalDate.of(2022, 12, 25);

        EventDTO event = new EventDTO(1, "Test Event", "This is a test event", testAddress, testDate ,testStartTime, testEndTime, EventStatus.Scheduled);

        createEvent(event);
    }

}

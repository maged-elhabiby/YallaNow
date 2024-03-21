package org.ucalgary.events_microservice.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {
    // Attributes
    private int eventID;
    private int groupID;
    private String eventTitle;
    private String eventDescription;
    private AddressDTO location;
    private LocalDate eventDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private EventStatus status;
    private int count;
    private int capacity;
    private int imageID;
    
    // Constructors
    public EventDTO(){}

    public EventDTO(int eventID, int groupID, 
                    String eventTitle, String eventDescription, 
                    AddressDTO location, LocalDate eventDate,
                    LocalTime eventStartTime, LocalTime eventEndTime,
                    EventStatus status, int count, int capacity, int imageID) {
        this.eventID = eventID;
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.count = count;
        this.capacity = capacity;
        this.imageID = imageID;
    }

    public EventDTO(int groupID, String eventTitle, 
                    String eventDescription, AddressDTO location, 
                    LocalDate eventDate, LocalTime eventStartTime, 
                    LocalTime eventEndTime,EventStatus status, 
                    int count, int capacity, int imageID) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.count = count;
        this.capacity = capacity;
        this.imageID = imageID;
    }

    // Getters
    public final int getEventID() {return eventID;}
    public final int getGroupID() {return groupID;}
    public final String getEventTitle() {return eventTitle;}
    public final String getEventDescription() {return eventDescription;}
    public final AddressDTO getLocation() {return location;}
    public final LocalDate getEventDate() {return eventDate;}
    public final LocalTime getEventStartTime() {return eventStartTime;}
    public final LocalTime getEventEndTime() {return eventEndTime;}
    public final EventStatus getStatus() {return status;}
    public final int getCount() {return count;}
    public final int getCapacity() {return capacity;}
    public final int getAddressID() {return location.getAddressID();}
    public final int getImageID() {return imageID;}

    // Setters
    public void setGroupID(final int groupID) {this.groupID = groupID;}
    public void setEventTitle(final String eventTitle) {this.eventTitle = eventTitle;}
    public void setEventDescription(final String eventDescription) {this.eventDescription = eventDescription;}
    public void setLocation(final AddressDTO location) {this.location = location;}
    public void setEventDate(final LocalDate eventDate) {this.eventDate = eventDate;}
    public void setEventStartTime(final LocalTime eventStartTime) {this.eventStartTime = eventStartTime;}
    public void setEventEndTime(final LocalTime eventEndTime) {this.eventEndTime = eventEndTime;}
    public void setStatus(final EventStatus status) {this.status = status;}
    public void setCount(final int count) {this.count = count;}
    public void setCapacity(final int capacity) {this.capacity = capacity;}
    public void setImageID(final int imageID) {this.imageID = imageID;}
}
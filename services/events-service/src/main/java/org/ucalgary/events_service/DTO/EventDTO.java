package org.ucalgary.events_service.DTO;

import java.time.LocalDateTime;

public class EventDTO {
    // Attributes
    private int eventID;
    private int groupID;
    private String eventTitle;
    private String eventDescription;
    private AddressDTO location;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private EventStatus status;
    private int count;
    private int capacity;
    private String imageUrl;
    
    // Constructors
    public EventDTO(){}

    public EventDTO(int eventID, int groupID, 
                    String eventTitle, String eventDescription, 
                    AddressDTO location, LocalDateTime eventStartTime, LocalDateTime eventEndTime,
                    EventStatus status, int count, int capacity, String imageUrl) {
        this.eventID = eventID;
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.count = count;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }

    public EventDTO(int groupID, String eventTitle, 
                    String eventDescription, AddressDTO location, 
                    LocalDateTime eventStartTime,
                    LocalDateTime eventEndTime,EventStatus status,
                    int count, int capacity, String imageUrl) {
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.count = count;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }

    // Getters
    public final int getEventID() {return eventID;}
    public final int getGroupID() {return groupID;}
    public final String getEventTitle() {return eventTitle;}
    public final String getEventDescription() {return eventDescription;}
    public final AddressDTO getLocation() {return location;}
    public final LocalDateTime getEventStartTime() {return eventStartTime;}
    public final LocalDateTime getEventEndTime() {return eventEndTime;}
    public final EventStatus getStatus() {return status;}
    public final int getCount() {return count;}
    public final int getCapacity() {return capacity;}
    public final int getAddressID() {return location.getAddressID();}
    public final String getImageUrl() {return imageUrl;}

    // Setters
    public void setEventID(final int eventID) {this.eventID = eventID;}
    public void setGroupID(final int groupID) {this.groupID = groupID;}
    public void setEventTitle(final String eventTitle) {this.eventTitle = eventTitle;}
    public void setEventDescription(final String eventDescription) {this.eventDescription = eventDescription;}
    public void setLocation(final AddressDTO location) {this.location = location;}
    public void setEventStartTime(final LocalDateTime eventStartTime) {this.eventStartTime = eventStartTime;}
    public void setEventEndTime(final LocalDateTime eventEndTime) {this.eventEndTime = eventEndTime;}
    public void setStatus(final EventStatus status) {this.status = status;}
    public void setCount(final int count) {this.count = count;}
    public void setCapacity(final int capacity) {this.capacity = capacity;}
    public void setImageUrl(final String imageUrl) {this.imageUrl = imageUrl;}
}
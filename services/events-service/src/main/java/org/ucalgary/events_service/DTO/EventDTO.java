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
                    EventStatus status, int count, int capacity, String imageUrl)throws IllegalArgumentException {
        checkEvent(eventID, groupID, eventTitle, eventDescription, location, eventStartTime, eventEndTime, status, count, capacity, imageUrl);
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
                    int capacity, String imageUrl) throws IllegalArgumentException {
        checkEvent(groupID, eventTitle, eventDescription, location, eventStartTime, eventEndTime, status, capacity);
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }

    public EventDTO(int groupID, String eventTitle, 
                    String eventDescription, AddressDTO location, 
                    LocalDateTime eventStartTime,
                    LocalDateTime eventEndTime, EventStatus status,
                    int capacity) throws IllegalArgumentException{
        checkEvent(groupID, eventTitle, eventDescription, location, eventStartTime, eventEndTime, status, capacity);
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.capacity = capacity;
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

    private void checkEvent(Integer groupID, String eventTitle, String eventDescription, AddressDTO location, 
                    LocalDateTime eventStartTime, LocalDateTime eventEndTime, EventStatus status, 
                    int capacity) throws IllegalArgumentException {
        if(groupID == null){
            throw new IllegalArgumentException("Group ID cannot be null");
        }else if (eventTitle == null || eventTitle.isEmpty()) {
            throw new IllegalArgumentException("Event title cannot be empty.");
        }else if (eventDescription == null || eventDescription.isEmpty()) {
            throw new IllegalArgumentException("Event description cannot be empty.");
        }else if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }else if (eventStartTime == null) {
            throw new IllegalArgumentException("Event start time cannot be null.");
        }else if (eventEndTime == null) {
            throw new IllegalArgumentException("Event end time cannot be null.");
        }else if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }else if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
    }

    private void checkEvent(Integer eventID, Integer groupID, String eventTitle, String eventDescription, 
                    AddressDTO location, LocalDateTime eventStartTime, LocalDateTime eventEndTime, 
                    EventStatus status, int count, int capacity, String imageUrl) throws IllegalArgumentException {
        if(eventID == null){
            throw new IllegalArgumentException("Event ID cannot be null");
        }else if(groupID == null){
            throw new IllegalArgumentException("Group ID cannot be null");
        }else if (eventTitle == null || eventTitle.isEmpty()) {
            throw new IllegalArgumentException("Event title cannot be empty.");
        }else if (eventDescription == null || eventDescription.isEmpty()) {
            throw new IllegalArgumentException("Event description cannot be empty.");
        }else if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }else if (eventStartTime == null) {
            throw new IllegalArgumentException("Event start time cannot be null.");
        }else if (eventEndTime == null) {
            throw new IllegalArgumentException("Event end time cannot be null.");
        }else if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }else if (count < 0) {
            throw new IllegalArgumentException("Count must be greater than or equal to 0.");
        }else if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
    }
}
package org.ucalgary.events_microservice.DTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventDTO {
    private int eventID;
    private int groupID;
    private String eventTitle;
    private String eventDescription;
    private AddressDTO location;
    private LocalDate eventDate;
    private TimeDTO eventStartTime;
    private TimeDTO eventEndTime;
    private EventStatus status;
    private ArrayList<ParticipantDTO> participants;
    private int poll;
    private int capacity;

    // Below is an Event
    public EventDTO(int groupID, String eventTitle, String eventDescription, AddressDTO location, LocalDate eventDate,
        TimeDTO eventStartTime, TimeDTO eventEndTime,EventStatus status) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
    }

    // Below is an Event with a capacity
    public EventDTO(int groupID, String eventTitle, String eventDescription, AddressDTO location, 
        TimeDTO eventStartTime, TimeDTO eventEndTime, EventStatus status,int capacity, ArrayList<ParticipantDTO> participants) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.capacity = capacity;
        this.participants = participants;
    }

    // Below is a Suggested Event
    public EventDTO(int groupID, String eventTitle, String eventDescription, AddressDTO location, 
        TimeDTO eventStartTime, TimeDTO eventEndTime, EventStatus status, ArrayList<ParticipantDTO> participants, int poll) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.participants = participants;
        this.poll = poll;
    }

    // Below is a Suggested event with a capacity
    public EventDTO(int groupID, String eventTitle, String eventDescription, AddressDTO location, 
        TimeDTO eventStartTime, TimeDTO eventEndTime, EventStatus status, ArrayList<ParticipantDTO> participants, int poll, int capacity) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.participants = participants;
        this.poll = poll;
        this.capacity = capacity;
    }

    // Getters
    public final int getEventID() {return eventID;}
    public final int getGroupID() {return groupID;}
    public final String getEventTitle() {return eventTitle;}
    public final String getEventDescription() {return eventDescription;}
    public final AddressDTO getLocation() {return location;}
    public final LocalDate getEventDate() {return eventDate;}
    public final TimeDTO getEventStartTime() {return eventStartTime;}
    public final TimeDTO getEventEndTime() {return eventEndTime;}
    public final EventStatus getStatus() {return status;}
    public final ArrayList<ParticipantDTO> getParticipants() {return participants;}
    public final int getPoll() {return poll;}
    public final int getCapacity() {return capacity;}

    // Setters
    public void setGroupID(final int groupID) {this.groupID = groupID;}
    public void setEventTitle(final String eventTitle) {this.eventTitle = eventTitle;}
    public void setEventDescription(final String eventDescription) {this.eventDescription = eventDescription;}
    public void setLocation(final AddressDTO location) {this.location = location;}
    public void setEventDate(final LocalDate eventDate) {this.eventDate = eventDate;}
    public void setEventStartTime(final TimeDTO eventStartTime) {this.eventStartTime = eventStartTime;}
    public void setEventEndTime(final TimeDTO eventEndTime) {this.eventEndTime = eventEndTime;}
    public void setStatus(final EventStatus status) {this.status = status;}
    public void setParticipants(final ArrayList<ParticipantDTO> participants) {this.participants = participants;}
    public void setPoll(final int poll) {this.poll = poll;}
    public void setCapacity(final int capacity) {this.capacity = capacity;}

    
}
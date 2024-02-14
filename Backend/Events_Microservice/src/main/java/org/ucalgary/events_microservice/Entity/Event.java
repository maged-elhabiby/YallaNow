package org.ucalgary.events_microservice.Entity;

import java.util.ArrayList;

public class Event {
    private int eventID;
    private int groupID;
    private String eventTitle;
    private String eventDescription;
    private Address location;
    private Time eventStartTime;
    private Time eventEndTime;
    private EventStatus status;
    private ArrayList<Participant> participants;
    private int poll;
    private int capacity;

    // Below is an Event
    public Event(int groupID, String eventTitle, String eventDescription, Address location, 
        Time eventStartTime, Time eventEndTime,EventStatus status) {

        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
    }

    // Below is an Event with a capacity
    public Event(int groupID, String eventTitle, String eventDescription, Address location, 
        Time eventStartTime, Time eventEndTime, EventStatus status,int capacity, ArrayList<Participant> participants) {

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
    public Event(int groupID, String eventTitle, String eventDescription, Address location, 
        Time eventStartTime, Time eventEndTime, EventStatus status, ArrayList<Participant> participants, int poll) {

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
    public Event(int groupID, String eventTitle, String eventDescription, Address location, 
        Time eventStartTime, Time eventEndTime, EventStatus status, ArrayList<Participant> participants, int poll, int capacity) {

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
    public final Address getLocation() {return location;}
    public final Time getEventStartTime() {return eventStartTime;}
    public final Time getEventEndTime() {return eventEndTime;}
    public final EventStatus getStatus() {return status;}
    public final ArrayList<Participant> getParticipants() {return participants;}
    public final int getPoll() {return poll;}
    public final int getCapacity() {return capacity;}

    // Setters
    public void setGroupID(final int groupID) {this.groupID = groupID;}
    public void setEventTitle(final String eventTitle) {this.eventTitle = eventTitle;}
    public void setEventDescription(final String eventDescription) {this.eventDescription = eventDescription;}
    public void setLocation(final Address location) {this.location = location;}
    public void setEventTime(final Time eventStartTime) {this.eventStartTime = eventStartTime;}
    public void setEventEndTime(final Time eventEndTime) {this.eventEndTime = eventEndTime;}
    public void setStatus(final EventStatus status) {this.status = status;}
    public void setParticipants(final ArrayList<Participant> participants) {this.participants = participants;}
    public void setPoll(final int poll) {this.poll = poll;}
    public void setCapacity(final int capacity) {this.capacity = capacity;}

    
}
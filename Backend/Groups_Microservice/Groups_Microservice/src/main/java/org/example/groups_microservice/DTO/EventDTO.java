package org.example.groups_microservice.DTO;

public class EventDTO {
    private int eventID;
    private String eventName;
    private int groupID;

    public EventDTO(int eventID, String eventName, int groupID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.groupID = groupID;
    }

    public EventDTO() {

    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }


}

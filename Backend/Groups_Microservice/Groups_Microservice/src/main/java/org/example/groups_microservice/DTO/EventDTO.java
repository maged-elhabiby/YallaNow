package org.example.groups_microservice.DTO;

public class EventDTO {
    private Integer eventID;
    private String eventName;
    private Integer groupID;
    private String groupName;


    public EventDTO(Integer eventID, String eventName, Integer groupID,String groupName) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.groupID = groupID;
        this.groupName = groupName;

    }

    public EventDTO() {

    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }



}

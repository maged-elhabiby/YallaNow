package org.example.groups_microservice.DTO;

public class EventDTO {
    private Integer eventID;
    private Integer globalEventID;
    private Integer groupID;
    private String groupName;


    public EventDTO(Integer eventID, Integer globalEventID, Integer groupID,String groupName,GroupDTO group) {
        this.eventID = eventID;
        this.globalEventID = globalEventID;
        this.groupID = group.getGroupID();
        this.groupName = group.getGroupName();

    }

    public EventDTO() {

    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Integer getGlobalEventID() {
        return globalEventID;
    }

    public void setGlobalEventID(Integer globalEventID) {
        this.globalEventID = globalEventID;
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

package org.example.groups_microservice.DTO;

import java.util.List;

public class GroupDTO {
    private int groupID;
    private String groupName;
    private List<GroupMemberDTO> groupMembers;
    private List<EventDTO> events;

    public GroupDTO(int groupID, String groupName, List<GroupMemberDTO> groupMembers, List<EventDTO> events) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.events = events;
    }

    public GroupDTO() {

    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GroupMemberDTO> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMemberDTO> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public void addGroupMember(GroupMemberDTO groupMember) {
        this.groupMembers.add(groupMember);
    }

    public void removeGroupMember(GroupMemberDTO groupMember) {
        this.groupMembers.remove(groupMember);
    }
    public void addEvent(EventDTO event) {
        this.events.add(event);
    }

    public void removeEvent(EventDTO event) {
        this.events.remove(event);
    }


}

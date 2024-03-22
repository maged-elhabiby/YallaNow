package org.example.groups_microservice.DTO;

import java.util.List;

public class GroupPubSub {
    private int groupID;
    private String groupName;
    private List<GroupMemberDTO> groupMembers;
    private List<EventDTO> events;

    public GroupPubSub(int groupID, String groupName, List<GroupMemberDTO> groupMembers, List<EventDTO> events) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.events = events;

    }

    public GroupPubSub() {
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

    public void addGroupMember(GroupMemberDTO member) {
        groupMembers.add(member);
    }

    public void addEvent(EventDTO event) {
        events.add(event);
    }

    public void removeGroupMember(GroupMemberDTO member) {
        groupMembers.remove(member);
    }

    public void removeEvent(EventDTO event) {
        events.remove(event);
    }



}

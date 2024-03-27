package org.example.groups_microservice.Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
    * This class is used to represent the groups entity,Used to Store, Retrieve, Update and Delete the groups object.
 */




@Entity
@Table(name = "groups_table")
public class GroupEntity {

    // attributes of the groups entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer groupID;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "member_count")
    private Integer memberCount;

    // relationships with other entities
    @OneToMany(mappedBy = "group",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMemberEntity> groupMembers;

    @OneToMany(mappedBy = "group",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;

    // constructors
    public GroupEntity() {
        this.groupMembers = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public GroupEntity(Integer groupID, String groupName, List<GroupMemberEntity> groupMembers, List<EventEntity> events, boolean isPrivate) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers != null ? groupMembers : new ArrayList<>();
        this.events = events != null ? events : new ArrayList<>();
        this.isPrivate = isPrivate;
        this.memberCount = groupMembers.size();

    }

    public GroupEntity(Integer groupID, String groupName, List<GroupMemberEntity> groupMembers,boolean isPrivate) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.isPrivate = isPrivate;
        this.memberCount = groupMembers.size();
    }


    // Getters and Setters
    public int getGroupID() {
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
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<GroupMemberEntity> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMemberEntity> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<EventEntity> getEvents() {
        return events;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }



    public void setEvents(List<EventEntity> events) {
        this.events = events;
    }
    public void addGroupMember(GroupMemberEntity member) {
        groupMembers.add(member);
        member.setGroup(this);
    }

    // Helper method for adding an event
    public void addEvent(EventEntity event) {
        events.add(event);
        event.setGroup(this);
    }
    public void removeGroupMember(GroupMemberEntity member) {
        groupMembers.remove(member);
        member.setGroup(null);
    }



}


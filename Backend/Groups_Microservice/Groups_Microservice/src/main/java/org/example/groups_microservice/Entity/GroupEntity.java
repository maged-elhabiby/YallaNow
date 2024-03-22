package org.example.groups_microservice.Entity;
import jakarta.persistence.*;
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

    // relationships with other entities
    @OneToMany(mappedBy = "group",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<GroupMemberEntity> groupMembers;

    @OneToMany(mappedBy = "group",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EventEntity> events;

    // constructors
    public GroupEntity() {
    }

    public GroupEntity(int groupID, String groupName, List<GroupMemberEntity> groupMembers, List<EventEntity> events) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.events = events;
    }

    public GroupEntity(int groupID, String groupName, List<GroupMemberEntity> groupMembers) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
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

    public List<GroupMemberEntity> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMemberEntity> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<EventEntity> getEvents() {
        return events;
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


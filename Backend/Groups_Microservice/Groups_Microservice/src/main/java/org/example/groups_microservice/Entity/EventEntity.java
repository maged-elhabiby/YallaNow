package org.example.groups_microservice.Entity;

import jakarta.persistence.*;

/**
 * EventsEntity, an entity class for events in the groups microservice.
 */
@Entity
@Table(name = "events")
public class EventEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventID;

    @Column(name = "event_name")
    private String eventName;





    //Relationships with other entities
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true) // Ensures the foreign key relation
    private GroupEntity group;

    // add group name from group entity to store the group name



    // uses group name from group entity to store the group name


    public EventEntity() {
    }

    public EventEntity(Integer eventID, String eventName, GroupEntity group) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.group = group;
    }

    // Getters and Setters

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

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }




}

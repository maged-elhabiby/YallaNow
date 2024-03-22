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
    @JoinColumn(name = "group_id", nullable = false) // Ensures the foreign key relation
    private GroupEntity group;

    public EventEntity() {
    }

    public EventEntity(int eventID, String eventName) {
        this.eventID = eventID;
        this.eventName = eventName;
    }

    // Getters and Setters

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

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }



}

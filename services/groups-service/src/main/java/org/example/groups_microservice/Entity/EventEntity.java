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

    @Column(name = "global_event_id")
    private Integer globalEventID;


    //Relationships with other entities
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true) // Ensures the foreign key relation
    private GroupEntity group;




    public EventEntity() {
    }

    public EventEntity(Integer eventID, Integer globalEventID, GroupEntity group) {
        this.eventID = eventID;
        this.globalEventID = globalEventID;
        this.group = group;
    }

    // Getters and Setters

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

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }




}

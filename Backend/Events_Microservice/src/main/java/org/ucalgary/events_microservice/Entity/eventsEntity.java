package org.ucalgary.events_microservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import org.ucalgary.events_microservice.DTO.EventDTO;

@Entity
@Table(name = "event_table")
public class eventsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int eventID;

    @Column(name = "group_id")
    private int groupID;
    @Column(name = "event_title")
    private String eventTitle;
    @Column(name = "event_description")
    private String eventDescription;
    @Column(name = "location")
    private String location;
    @Column(name = "event_date")
    private String eventDate;
    @Column(name = "event_start_time")
    private String eventStartTime;
    @Column(name = "event_end_time")
    private String eventEndTime;
    @Column(name = "status")
    private String status;
    @Column(name = "poll")
    private int poll;
    @Column(name = "capacity")
    private int capacity;

    public eventsEntity() {
    }

    public eventsEntity(int groupID, String eventTitle, String eventDescription, String location, 
                String eventStartTime, String eventEndTime, String status, int poll, int capacity) {
        this.groupID = groupID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.poll = poll;
        this.capacity = capacity;
    }

}

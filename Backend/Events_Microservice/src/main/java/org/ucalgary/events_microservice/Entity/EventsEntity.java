package org.ucalgary.events_microservice.Entity;

import java.time.LocalDateTime;
import java.util.List;
import org.ucalgary.events_microservice.DTO.EventStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * EventsEntity, Used to Create and Store EventsEntity Objects in MySQL Database
 */
@Entity
@Table(name = "event_table")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventsEntity {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "event_title")
    private String eventTitle;
    @Column(name = "event_description")
    private String eventDescription;
    @Column(name = "location_id")
    private Integer locationId;
    @Column(name = "event_start_time")
    private LocalDateTime eventStartTime;
    @Column(name = "event_end_time")
    private LocalDateTime eventEndTime;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Column(name = "count")
    private Integer count;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "image_id")
    private Integer imageId;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "address_id", insertable = false, updatable = false)
    private AddressEntity address;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantEntity> participants;
    
    // Constructors
    public EventsEntity() {
    }

    public EventsEntity(Integer eventId, Integer groupId, 
                        String eventTitle, String eventDescription, 
                        Integer locationId,
                        LocalDateTime eventStartTime, LocalDateTime eventEndTime,
                        EventStatus status, Integer count, Integer capacity, Integer imageId) {
        this.eventId = eventId;
        this.groupId = groupId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.locationId = locationId;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.status = status;
        this.count = count;
        this.capacity = capacity;
        this.imageId = imageId;
    }

    // Getters and setters
    public Integer getEventId() {return eventId;}
    public Integer getGroupId() {return groupId;}
    public String getEventTitle() {return eventTitle;}
    public String getEventDescription() {return eventDescription;}
    public Integer getLocationId() {return locationId;}
    public LocalDateTime getEventStartTime() {return eventStartTime;}
    public LocalDateTime getEventEndTime() {return eventEndTime;}
    public EventStatus getStatus() {return status;}
    public Integer getCount() {return count;}
    public Integer getCapacity() {return capacity;}
    public Integer getImageId() {return imageId;}
    public AddressEntity getAddress() {return address;}

    public void setEventId(Integer eventId) {this.eventId = eventId;}
    public void setGroupId(Integer groupId) {this.groupId = groupId;}
    public void setEventTitle(String eventTitle) {this.eventTitle = eventTitle;}
    public void setEventDescription(String eventDescription) {this.eventDescription = eventDescription;}
    public void setLocationId(Integer locationId) {this.locationId = locationId;}
    public void setEventStartTime(LocalDateTime eventStartTime) {this.eventStartTime = eventStartTime;}
    public void setEventEndTime(LocalDateTime eventEndTime) {this.eventEndTime = eventEndTime;}
    public void setStatus(EventStatus status) {this.status = status;}
    public void setCount(Integer count) {this.count = count;}
    public void setCapacity(Integer capacity) {this.capacity = capacity;}
    public void setAddress(AddressEntity address) {this.address = address;}
    public void setImageId(Integer imageId) {this.imageId = imageId;}
}


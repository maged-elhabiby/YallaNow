package org.ucalgary.events_microservice.Entity;

import org.ucalgary.events_microservice.DTO.ParticipantStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "participant")
public class ParticipantEntity {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;
    @Column(name = "user_id")
    private Integer userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_status")
    private ParticipantStatus participantStatus;

    // RelationShips
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventsEntity event;

    // Constructors
    public ParticipantEntity() {
    }

    public ParticipantEntity(Integer userId, ParticipantStatus participantStatus, EventsEntity event) {
        this.userId = userId;
        this.participantStatus = participantStatus;
        this.event = event;
    }

    // Getters and setters
    public Integer getParticipantId() {return participantId;}
    public Integer getUserId() {return userId;}
    public ParticipantStatus getParticipantStatus() {return participantStatus;}
    public EventsEntity getEvent() {return event;}

    public void setParticipantId(Integer participantId) {this.participantId = participantId;}
    public void setUserId(Integer userId) {this.userId = userId;}
    public void setParticipantStatus(ParticipantStatus participantStatus) {this.participantStatus = participantStatus;}
    public void setEvent(EventsEntity event) {this.event = event;}
}

package org.ucalgary.events_service.Entity;

import org.ucalgary.events_service.DTO.ParticipantStatus;

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

/**
 * ParticipantEntity, Used to Create and Store ParticipantEntity Objects in MySQL Database
 */
@Entity
@Table(name = "participant")
public class ParticipantEntity {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;
    @Column(name = "user_id")
    private String userId;
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

    public ParticipantEntity(String userId, ParticipantStatus participantStatus, EventsEntity event)throws IllegalArgumentException {
        ValidateInput(event, userId, participantStatus);
        this.userId = userId;
        this.participantStatus = participantStatus;
        this.event = event;
    }

    // Getters and setters
    public Integer getParticipantId() {return participantId;}
    public String getUserId() {return userId;}
    public ParticipantStatus getParticipantStatus() {return participantStatus;}
    public EventsEntity getEvent() {return event;}

    public void setParticipantId(Integer participantId) {this.participantId = participantId;}
    public void setUserId(String userId) {this.userId = userId;}
    public void setParticipantStatus(ParticipantStatus participantStatus) {this.participantStatus = participantStatus;}
    public void setEvent(EventsEntity event) {this.event = event;}

    /**
     * Validate the input
     * @param event
     * @param userId
     * @param participantStatus
     * @throws IllegalArgumentException
     */
    private void ValidateInput(EventsEntity event, String userId, ParticipantStatus participantStatus) throws IllegalArgumentException{
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        } else if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        } else if (participantStatus == null) {
            throw new IllegalArgumentException("Participant Status cannot be null");
        }
    }
}

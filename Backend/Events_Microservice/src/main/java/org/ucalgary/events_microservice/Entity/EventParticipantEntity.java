package org.ucalgary.events_microservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_participant")
public class EventParticipantEntity {

    @Id
    @Column(name = "event_id")
    private Integer eventId;

    @Id
    @Column(name = "participant_id")
    private Integer participantId;

    // Getters and setters
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }
}

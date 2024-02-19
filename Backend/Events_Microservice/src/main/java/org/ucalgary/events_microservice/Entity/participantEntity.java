package org.ucalgary.events_microservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_participants")
public class participantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int eventID;

    @Column(name = "event_id")
    private int groupID;
    @Column(name = "user_id")
    private int participantID;
    @Column(name = "status")
    private String participantStatus;
    
    public participantEntity() {
    }

    public participantEntity(int groupID, int participantID, String participantStatus) {
        this.groupID = groupID;
        this.participantID = participantID;
        this.participantStatus = participantStatus;
    }

    public int getEventID() {return eventID;}
    public int getGroupID() {return groupID;}
    public int getParticipantID() {return participantID;}
    public String getParticipantStatus() {return participantStatus;}

    public void setGroupID(int groupID) {this.groupID = groupID;}
    public void setParticipantID(int participantID) {this.participantID = participantID;}
    public void setParticipantStatus(String participantStatus) {this.participantStatus = participantStatus;}

}

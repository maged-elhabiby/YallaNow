package org.ucalgary.events_microservice.DTO;

public class ParticipantDTO {
    // Attributes
    private int participantID;
    private int userid;
    private int eventid;
    private ParticipantStatus ParticipantStatus;

    // Constructors
    public ParticipantDTO() {}

    public ParticipantDTO(int userid,int event_id ,ParticipantStatus ParticipantStatus) {
        this.userid = userid;
        this.eventid = event_id;
        this.ParticipantStatus = ParticipantStatus;
    }

    public ParticipantDTO(int participantID, int userid, int eventid, ParticipantStatus ParticipantStatus) {
        this.participantID = participantID;
        this.userid = userid;
        this.eventid = eventid;
        this.ParticipantStatus = ParticipantStatus;
    }

    // Getters and setters
    public int getParticipantID() {return participantID;}
    public int getUserid() {return userid;}
    public int getEventid() {return eventid;}
    public ParticipantStatus getParticipantStatus() {return ParticipantStatus;}

    public void setUserid(int userid) {this.userid = userid;}
    public void setEventid(int eventid) {this.eventid = eventid;}
    public void setParticipantStatus(ParticipantStatus ParticipantStatus) {this.ParticipantStatus = ParticipantStatus;}

}

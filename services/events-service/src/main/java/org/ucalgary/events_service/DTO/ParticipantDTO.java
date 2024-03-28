package org.ucalgary.events_service.DTO;

public class ParticipantDTO {
    // Attributes
    private int participantID;
    private String userid;
    private int eventid;
    private ParticipantStatus ParticipantStatus;

    // Constructors
    public ParticipantDTO() {}

    public ParticipantDTO(String userid,int event_id ,ParticipantStatus ParticipantStatus) {
        this.userid = userid;
        this.eventid = event_id;
        this.ParticipantStatus = ParticipantStatus;
    }

    public ParticipantDTO(int participantID, String userid, int eventid, ParticipantStatus ParticipantStatus) {
        this.participantID = participantID;
        this.userid = userid;
        this.eventid = eventid;
        this.ParticipantStatus = ParticipantStatus;
    }

    // Getters and setters
    public final  int getParticipantID() {return participantID;}
    public final  String getUserid() {return userid;}
    public final  int getEventid() {return eventid;}
    public final ParticipantStatus getParticipantStatus() {return ParticipantStatus;}

    public void setParticipantID(final int participantID) {this.participantID = participantID;}
    public void setUserid(final String userid) {this.userid = userid;}
    public void setEventid(final int eventid) {this.eventid = eventid;}
    public void setParticipantStatus(final ParticipantStatus ParticipantStatus) {this.ParticipantStatus = ParticipantStatus;}

}

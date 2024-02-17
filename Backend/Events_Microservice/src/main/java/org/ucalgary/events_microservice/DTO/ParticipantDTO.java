package org.ucalgary.events_microservice.DTO;

public class ParticipantDTO {
    private int userid;
    private int eventid;
    private ParticipantStatus ParticipantStatus;

    public ParticipantDTO(int userid, ParticipantStatus ParticipantStatus) {
        this.userid = userid;
        this.ParticipantStatus = ParticipantStatus;
    }

    public int getUserid() {return userid;}
    public int getEventid() {return eventid;}
    public ParticipantStatus getParticipantStatus() {return ParticipantStatus;}

    public void setUserid(int userid) {this.userid = userid;}
    public void setEventid(int eventid) {this.eventid = eventid;}
    public void setParticipantStatus(ParticipantStatus ParticipantStatus) {this.ParticipantStatus = ParticipantStatus;}

}

package org.ucalgary.events_microservice.Entity;

public class Participant {
    private int userid;
    private ParticipantStatus ParticipantStatus;

    public Participant(int userid, ParticipantStatus ParticipantStatus) {
        this.userid = userid;
        this.ParticipantStatus = ParticipantStatus;
    }

    public int getUserid() {return userid;}
    public ParticipantStatus getParticipantStatus() {return ParticipantStatus;}

    public void setUserid(int userid) {this.userid = userid;}
    public void setParticipantStatus(ParticipantStatus ParticipantStatus) {this.ParticipantStatus = ParticipantStatus;}

}

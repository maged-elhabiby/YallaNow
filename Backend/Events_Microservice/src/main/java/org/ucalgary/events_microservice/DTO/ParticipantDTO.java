package org.ucalgary.events_microservice.DTO;

public class ParticipantDTO {
    private int userid;
    private ParticipantStatus ParticipantStatus;

    public ParticipantDTO(int userid, ParticipantStatus ParticipantStatus) {
        this.userid = userid;
        this.ParticipantStatus = ParticipantStatus;
    }

    public int getUserid() {return userid;}
    public ParticipantStatus getParticipantStatus() {return ParticipantStatus;}

    public void setUserid(int userid) {this.userid = userid;}
    public void setParticipantStatus(ParticipantStatus ParticipantStatus) {this.ParticipantStatus = ParticipantStatus;}

}

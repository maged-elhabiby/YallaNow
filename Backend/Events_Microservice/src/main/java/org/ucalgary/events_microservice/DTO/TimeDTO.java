package org.ucalgary.events_microservice.DTO;

public class TimeDTO {
    private int hour;
    private int minute;
    private int second;

    public TimeDTO(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        this.second = 0;
    }

    public int getHour() {return hour;}
    public int getMinute() {return minute;}

    public void setHour(int hour) {this.hour = hour;}
    public void setMinute(int minute) {this.minute = minute;}


    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}

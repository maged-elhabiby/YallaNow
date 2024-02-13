package Events_MicroService;

public class Time {
    private int StartHour;
    private int StartMinute;
    private int EndHour;
    private int EndMinute;

    public Time(int StartHour, int StartMinute, int EndHour, int EndMinute) {
        this.StartHour = StartHour;
        this.StartMinute = StartMinute;
        this.EndHour = EndHour;
        this.EndMinute = EndMinute;
    }
    
    public int getStartHour() {return StartHour;}
    public int getStartMinute() {return StartMinute;}
    public int getEndHour() {return EndHour;}
    public int getEndMinute() {return EndMinute;}
    
    public void setStartHour(int StartHour) {this.StartHour = StartHour;}
    public void setStartMinute(int StartMinute) {this.StartMinute = StartMinute;}
    public void setEndHour(int EndHour) {this.EndHour = EndHour;}
    public void setEndMinute(int EndMinute) {this.EndMinute = EndMinute;}
}

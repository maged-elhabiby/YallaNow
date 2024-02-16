package org.ucalgary.events_microservice.DTO;

public class DateDTO {
    private int day;
    private int month;
    private int year;

    public DateDTO(int day, int month, int year) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Invalid year");
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {return day;}
    public int getMonth() {return month;}
    public int getYear() {return year;}

    public void setDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day");
        }
        this.day = day;
    }

    public void setMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }
        this.month = month;
    }

    public void setYear(int year) {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Invalid year");
        }
        this.year = year;
    }

}

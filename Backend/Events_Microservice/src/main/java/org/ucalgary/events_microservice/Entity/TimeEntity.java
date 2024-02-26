package org.ucalgary.events_microservice.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * TimeEntity, Used to Create and Store TimeEntity Objects in MySQL Database
 */
@Entity
@Table(name = "time")
public class TimeEntity {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    private Integer timeId;
    @Column(name = "hour")
    private Integer hour;
    @Column(name = "minute")
    private Integer minute;

    // Constructors
    public TimeEntity() {
    }

    public TimeEntity(Integer timeID, Integer hour, Integer minute) {
        this.timeId = timeID;
        this.hour = hour;
        this.minute = minute;
    }

    // Getters and setters
    public Integer getTimeID() {return timeId;}
    public Integer getHour() {return hour;}
    public Integer getMinute() {return minute;}

    public void setHour(Integer hour) {this.hour = hour;}
    public void setMinute(Integer minute) {this.minute = minute;}
}

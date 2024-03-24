package org.ucalgary.events_microservice.DTO;

import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import org.ucalgary.events_microservice.Entity.EventsEntity;

public class PubEvent {
    // Attributes
    private int eventID;
    private int groupID;
    private String eventTitle;
    private String eventDescription;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private String status;
    private int count;
    private int capacity;
    private String imageUrl;

    // Constructors
    public PubEvent() {}

    public PubEvent(EventsEntity event, RestTemplate restTemplate) {
        this.eventID = event.getEventId();
        this.groupID = event.getGroupId();
        this.eventTitle = event.getEventTitle();
        this.eventDescription = event.getEventDescription();
        this.street = event.getAddress().getStreet();
        this.city = event.getAddress().getCity();
        this.province = event.getAddress().getProvince();
        this.postalCode = event.getAddress().getPostalCode();
        this.country = event.getAddress().getCountry();
        this.eventStartTime = event.getEventStartTime();
        this.eventEndTime = event.getEventEndTime();
        this.status = event.getStatus().toString();
        this.count = event.getCount();
        this.capacity = event.getCapacity();
        try {
            String responseString = restTemplate.getForObject(
                "http://localhost:8081/microservice/images/GetImage/" + event.getImageId(),
                String.class
            );
    
            // Extract imageLink from the response string
            String imageLink = extractImageLink(responseString);
            this.imageUrl = imageLink;
        } catch (Exception e) {
            this.imageUrl = null;
            throw new RuntimeException(e.getMessage());
        }
    }

    // Getters and setters
    public final int getEventID() {return eventID;}
    public final int getGroupID() {return groupID;}
    public final String getEventTitle() {return eventTitle;}
    public final String getEventDescription() {return eventDescription;}
    public final String getStreet() {return street;}
    public final String getCity() {return city;}
    public final String getProvince() {return province;}
    public final String getPostalCode() {return postalCode;}
    public final String getCountry() {return country;}
    public final LocalDateTime getEventStartTime() {return eventStartTime;}
    public final LocalDateTime getEventEndTime() {return eventEndTime;}
    public final String getStatus() {return status;}
    public final int getCount() {return count;}
    public final int getCapacity() {return capacity;}
    public final String getImageUrl() {return imageUrl;}
    
    
    private String extractImageLink(String responseString) {
        // Find the index of "imageLink" in the response string
        int startIndex = responseString.indexOf("\"imageLink\":\"");
        if (startIndex == -1) {
            return null; // Image link not found
        }
    
        // Extract the substring containing the imageLink
        startIndex += "\"imageLink\":\"".length();
        int endIndex = responseString.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return null; // Image link not properly formatted
        }
    
        return responseString.substring(startIndex, endIndex);
    }
}

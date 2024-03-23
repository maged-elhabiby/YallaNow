package com.yallanow.analyticsservice.factories;


import com.yallanow.analyticsservice.helpers.RandomNameGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class EventMessageFactory {

    public static Map<String, Object> generateEventMessage(String operationType) {
        if (!Objects.equals(operationType, "ADD") && !Objects.equals(operationType, "UPDATE") && !Objects.equals(operationType, "DELETE")) {
            throw new IllegalArgumentException("Invalid operation type.");
        }
        Map<String, Object> message = new HashMap<>();
        message.put("operation", operationType);
        Map<String, Object> eventDetails = generateRandomEvent();
        message.put("event", eventDetails);
        return message;
    }

    private static Map<String, Object> generateRandomEvent() {
        Random random = new Random();

        Map<String, Object> eventDetails = new HashMap<>();
        eventDetails.put("eventId", String.valueOf(random.nextInt()));
        eventDetails.put("groupId", String.valueOf(random.nextInt()));
        eventDetails.put("groupName", RandomNameGenerator.generateRandomGroupName());
        eventDetails.put("eventTitle", RandomNameGenerator.generateRandomEventName());
        eventDetails.put("eventDescription", "Random Event Description");
        eventDetails.put("eventStartTime", "2023-01-01T00:00:00Z");
        eventDetails.put("eventEndTime", "2023-01-01T02:00:00Z");
        Map<String, Object> location = new HashMap<>();
        location.put("addressID", String.valueOf(random.nextInt()));
        location.put("street", "Random Street");
        location.put("city", "Random City");
        location.put("province", "Random Province");
        location.put("country", "Random Country");
        eventDetails.put("eventLocation", location);
        eventDetails.put("eventAttendeeCount", random.nextInt(100));
        eventDetails.put("eventCapacity", random.nextInt(200));
        eventDetails.put("eventStatus", "scheduled");
        eventDetails.put("eventImageUrl", "http://www.catster.com/wp-content/uploads/2017/08/A-fluffy-cat-looking-funny-surprised-or-concerned.jpg");

        return eventDetails;
    }

    // Event class to represent your schema
    public static class Event {
        private final String operation;
        private final String eventId;
        private final String groupId;
        private final String groupName;
        private final String eventTitle;
        private final String eventDescription;
        private final String startTime;
        private final String endTime;
        private final Location location;
        private final Integer attendeeCount;
        private final Integer capacity;
        private final String status;
        private final String imageUrl;

        public Event(String operation, String eventId, String groupId, String groupName, String eventTitle, String eventDescription, String startTime, String endTime, Location location, Integer attendeeCount, Integer capacity, String status, String imageUrl) {
            this.operation = operation;
            this.eventId = eventId;
            this.groupId = groupId;
            this.groupName = groupName;
            this.eventTitle = eventTitle;
            this.eventDescription = eventDescription;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
            this.attendeeCount = attendeeCount;
            this.capacity = capacity;
            this.status = status;
            this.imageUrl = imageUrl;
        }

        public String getEventId() {
            return eventId;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public Location getLocation() {
            return location;
        }

        public Integer getAttendeeCount() {
            return attendeeCount;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public String getStatus() {
            return status;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getOperation() {
            return operation;
        }
    }

    public static class Location {
        private final Integer addressID;
        private final Integer street;
        private final String city;
        private final String province;
        private final String country;

        public Location(Integer addressID, Integer street, String city, String province, String country) {
            this.addressID = addressID;
            this.street = street;
            this.city = city;
            this.province = province;
            this.country = country;
        }

        public Integer getAddressID() {
            return addressID;
        }

        public Integer getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }

        public String getProvince() {
            return province;
        }

        public String getCountry() {
            return country;
        }

    }
}

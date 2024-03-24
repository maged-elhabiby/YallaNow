package com.yallanow.analyticsservice.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallanow.analyticsservice.helpers.RandomNameGenerator;

import java.text.SimpleDateFormat;
import java.util.*;

public class EventMessageFactory {

    private static final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    static {
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Map<String, Object> generateEventMessage(String operationType, int eventId) {
        if (!Objects.equals(operationType, "ADD") && !Objects.equals(operationType, "UPDATE") && !Objects.equals(operationType, "DELETE")) {
            throw new IllegalArgumentException("Invalid operation type.");
        }

        // Create the outer structure of the Pub/Sub message
        Map<String, Object> pubSubMessage = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("operationType", operationType);
        pubSubMessage.put("attributes", attributes);

        // The data field is a JSON string, which will contain the event details
        String data = createEventData(operationType, eventId);
        pubSubMessage.put("data", data);

        return pubSubMessage;
    }

    private static String createEventData(String operationType, int eventId) {
        Map<String, Object> eventDetails = generateRandomEventDetails(operationType, eventId);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(eventDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting event details to JSON", e);
        }
    }

    private static Map<String, Object> generateRandomEventDetails(String operationType, int eventId) {
        Random random = new Random();

        // Create the event details according to the provided schema
        Map<String, Object> eventDetails = new HashMap<>();
        if (!"DELETE".equals(operationType)) {
            eventDetails.put("eventId", eventId);
            eventDetails.put("groupId", random.nextInt());
            eventDetails.put("eventTitle", RandomNameGenerator.generateRandomEventName());
            eventDetails.put("eventDescription", "Random Event Description");

            eventDetails.put("eventStartTime", isoFormat.format(new Date()));
            eventDetails.put("eventEndTime", isoFormat.format(new Date()));

            eventDetails.put("street", random.nextInt(1000)); // Assuming the street should be an integer
            eventDetails.put("city", "Random City");
            eventDetails.put("province", "Random Province");
            eventDetails.put("country", "Random Country");

            eventDetails.put("count", random.nextInt(100)); // Assuming "count" represents "attendeeCount"
            eventDetails.put("capacity", random.nextInt(200));
            eventDetails.put("status", "Scheduled");

            eventDetails.put("imageUrl", "https://storage.googleapis.com/tmp-bucket-json-data/eventImage.png");
        } else {
            eventDetails.put("eventId", eventId);
        }
        return eventDetails;
    }


}

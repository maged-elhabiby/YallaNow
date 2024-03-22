package com.yallanow.analyticsservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.models.Location;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ItemConverter implements Converter<Item> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> toRecombeeMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", item.getGroupId());
        map.put("groupName", item.getGroupName());
        map.put("eventTitle", item.getTitle());
        map.put("eventDescription", item.getDescription());

        map.put("eventStartTime", item.getStartTime().toString());
        map.put("eventEndTime", item.getEndTime().toString());

        map.put("eventLocationStreet", item.getLocation().getStreet());
        map.put("eventLocationCity", item.getLocation().getCity());
        map.put("eventLocationProvince", item.getLocation().getProvince());
        map.put("eventLocationCountry", item.getLocation().getCountry());

        map.put("eventAttendeeCount", item.getAttendeeCount());
        map.put("eventCapacity", item.getCapacity());
        map.put("eventStatus", item.getStatus());
        map.put("eventImageUrl", item.getImageUrl());
        return map;
    }

    @Override
    public Item fromPubsubMessage(String message) throws IOException {
        return fromMap(extractEvent(message));
    }

    private Map<String, Object> extractEvent(String message) throws com.fasterxml.jackson.core.JsonProcessingException {
        Map<String, Object> payload = objectMapper.readValue(message, Map.class);
        if (!payload.containsKey("event")) {
            throw new IllegalArgumentException("Message does not contain 'event' data");
        }
        Map<String, Object> eventData = (Map<String, Object>) payload.get("event");
        return eventData;
    }

    public String getIdFromPubSubMessage(String message) throws IOException {
        Map<String, Object> eventData = extractEvent(message);
        String eventId = String.valueOf(Optional.ofNullable(eventData.get("eventId"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'eventId' in event data")));
        return eventId;
    }

    @Override
    public Item fromMap(Map<String, Object> map) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try {
            String eventId = String.valueOf(Optional.ofNullable(map.get("eventId"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventId' in event data")));
            String groupId = String.valueOf(Optional.ofNullable(map.get("groupId"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'groupId' in event data")));
            String groupName = Optional.ofNullable((String) map.get("groupName"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'groupName' in event data"));
            String eventTitle = Optional.ofNullable((String) map.get("eventTitle"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventTitle' in event data"));
            String eventDescription = Optional.ofNullable((String) map.get("eventDescription"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventDescription' in event data"));
            LocalDateTime startTime = LocalDateTime.parse(Optional.ofNullable((String) map.get("eventStartTime"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventStartTime' in event data")), formatter);
            LocalDateTime endTime = LocalDateTime.parse(Optional.ofNullable((String) map.get("eventEndTime"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventEndTime' in event data")), formatter);
            Location location = LocationConverter.fromMap(Optional.ofNullable((Map<String, Object>) map.get("eventLocation"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventLocation' in event data")));
            Integer eventAttendeeCount = Optional.ofNullable((Integer) map.get("eventAttendeeCount"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventAttendeeCount' in event data"));
            Integer eventCapacity = Optional.ofNullable((Integer) map.get("eventCapacity"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventCapacity' in event data"));
            String eventStatus = Optional.ofNullable((String) map.get("eventStatus"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventStatus' in event data"));
            String eventImageUrl = Optional.ofNullable((String) map.get("eventImageUrl"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventImageUrl' in event data"));

            return new Item(
                    eventId,
                    groupId,
                    groupName,
                    eventTitle,
                    eventDescription,
                    startTime,
                    endTime,
                    location,
                    eventAttendeeCount,
                    eventCapacity,
                    eventStatus,
                    eventImageUrl
            );
        } catch (Exception e) {
            throw new IOException("Error constructing item from map: " + e.getMessage(), e);
        }
    }


}

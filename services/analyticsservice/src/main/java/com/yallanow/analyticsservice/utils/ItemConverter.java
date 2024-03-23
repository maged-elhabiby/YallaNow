package com.yallanow.analyticsservice.utils;

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
public class ItemConverter {

    public Map<String, Object> convertItemToRecombeeMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", item.getGroupId());
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

    public String getIdFromPubSubMessage(Map<String, Object> map) {
        return String.valueOf(Optional.ofNullable(map.get("eventId"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'eventId' in event data")));
    }

    public Item getItemFromPubsubMessage(Map<String, Object> map) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try {
            String eventId = String.valueOf(Optional.ofNullable(map.get("eventId"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventId' in event data")));

            String groupId = String.valueOf(Optional.ofNullable(map.get("groupId"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'groupId' in event data")));

            String eventTitle = Optional.ofNullable((String) map.get("eventTitle"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventTitle' in event data"));

            String eventDescription = Optional.ofNullable((String) map.get("eventDescription"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventDescription' in event data"));

            LocalDateTime startTime = LocalDateTime.parse(Optional.ofNullable((String) map.get("eventStartTime"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventStartTime' in event data")), formatter);

            LocalDateTime endTime = LocalDateTime.parse(Optional.ofNullable((String) map.get("eventEndTime"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'eventEndTime' in event data")), formatter);

            String eventStreet = String.valueOf(Optional.ofNullable(map.get("street"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'street' in event data")));

            String eventCity = Optional.ofNullable((String) map.get("city"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'city' in event data"));

            String eventProvince = Optional.ofNullable((String) map.get("province"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'province' in event data"));

            String eventCountry = Optional.ofNullable((String) map.get("country"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'country' in event data"));

            Location location = new Location(
                    eventStreet,
                    eventCity,
                    eventProvince,
                    eventCountry
            );

            Integer eventAttendeeCount = Optional.ofNullable((Integer) map.get("count"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'count' in event data"));

            Integer eventCapacity = Optional.ofNullable((Integer) map.get("capacity"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'capacity' in event data"));

            String eventStatus = Optional.ofNullable((String) map.get("status"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'status' in event data"))
                    .toLowerCase(); // Convert the status to lowercase

            String eventImageUrl = Optional.ofNullable((String) map.get("imageUrl"))
                    .orElseThrow(() -> new IllegalArgumentException("Missing 'imageUrl' in event data"));

            return new Item(
                    eventId,
                    groupId,
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

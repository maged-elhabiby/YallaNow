package com.yallanow.analyticsservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.models.Location;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemConverter implements Converter<Item> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> toRecombeeMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", item.getItemId());
        map.put("groupId", item.getGroupId());
        map.put("groupName", item.getGroupName());
        map.put("title", item.getTitle());
        map.put("description", item.getDescription());

        map.put("startTime", item.getStartTime().toString());
        map.put("endTime", item.getEndTime().toString());

        map.put("street", item.getLocation().getStreet());
        map.put("city", item.getLocation().getCity());
        map.put("province", item.getLocation().getProvince());
        map.put("county", item.getLocation().getCountry());

        map.put("attendeeCount", item.getAttendeeCount());
        map.put("capacity", item.getCapacity());
        map.put("status", item.getStatus());
        map.put("imageUrl", item.getImageUrl());
        return map;
    }

    @Override
    public Item fromPubsubMessage(String message) throws IOException {

            Map<String, Object> payload = objectMapper.readValue(message, Map.class);
            Map<String, Object> itemData = (Map<String, Object>) payload.get("item");
            return fromMap(itemData);

    }

    @Override
    public Item fromMap(Map<String, Object> map) {
        String itemId = (String) map.get("eventId");
        String groupId = (String) map.get("groupId");
        String groupName = (String) map.get("groupName");
        String title = (String) map.get("eventTitle");
        String description = (String) map.get("eventDescription");
        LocalDateTime startTime = LocalDateTime.parse((String) map.get("startTime"));
        LocalDateTime endTime = LocalDateTime.parse((String) map.get("endTime"));
        Location location = LocationConverter.fromMap((Map<String, Object>) map.get("location"));

        int attendeeCount = (Integer) map.get("attendeeCount");
        int capacity = (Integer) map.get("capacity");
        String imageUrl = (String) map.get("imageUrl");
        String status = (String) map.get("status");
        List<String> categories = (List<String>) map.get("categories");

        return new Item(itemId, groupId, groupName, title, description, startTime, endTime,
                location, attendeeCount, categories, capacity, status, imageUrl);
    }

}

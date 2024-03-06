package com.yallanow.analyticsservice.models;

import java.time.LocalDateTime;
import java.util.List;

public class Item {
    private String itemId;
    private String groupId;
    private String groupName;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String eventType;
    private int attendeeCount;

    private List<String> categories;

    public Item(String itemId, String groupId, String groupName, String title, String description, LocalDateTime startTime,
                LocalDateTime endTime, String location, String eventType, int attendeeCount, List<String> categories) {
        this.itemId = itemId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.eventType = eventType;
        this.attendeeCount = attendeeCount;
        this.categories = categories;
    }

    // Getters and setters for all fields

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}

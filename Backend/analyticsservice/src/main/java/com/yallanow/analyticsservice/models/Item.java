package com.yallanow.analyticsservice.models;

import java.time.LocalDateTime;

/**
 * Represents an item in the YallaNow application.
 */
public class Item {
    private String itemId;
    private String groupId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Location location;
    private int attendeeCount;
    private int capacity;
    private String status;
    private String imageUrl;

    /**
     * Constructs a new Item object with the specified parameters.
     *
     * @param itemId        the ID of the item
     * @param groupId       the ID of the group the item belongs to
     * @param title         the title of the item
     * @param description   the description of the item
     * @param startTime     the start time of the item
     * @param endTime       the end time of the item
     * @param location      the location of the item
     * @param attendeeCount the number of attendees for the item
     * @param capacity      the capacity of the item
     * @param status        the status of the item
     * @param imageUrl      the URL of the item's image
     */
    public Item(String itemId, String groupId, String title, String description, LocalDateTime startTime,
                LocalDateTime endTime, Location location, int attendeeCount,
                int capacity, String status, String imageUrl) {
        this.itemId = itemId;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendeeCount = attendeeCount;
        this.capacity = capacity;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    // Getters and setters for all fields

    /**
     * Returns the URL of the item's image.
     *
     * @return the URL of the item's image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the item's image.
     *
     * @param imageUrl the URL of the item's image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the ID of the item.
     *
     * @return the ID of the item
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the ID of the item.
     *
     * @param itemId the ID of the item
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the title of the item.
     *
     * @return the title of the item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the item.
     *
     * @param title the title of the item
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the item.
     *
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description the description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the start time of the item.
     *
     * @return the start time of the item
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the item.
     *
     * @param startTime the start time of the item
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the item.
     *
     * @return the end time of the item
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the item.
     *
     * @param endTime the end time of the item
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the location of the item.
     *
     * @return the location of the item
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the item.
     *
     * @param location the location of the item
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns the number of attendees for the item.
     *
     * @return the number of attendees for the item
     */
    public int getAttendeeCount() {
        return attendeeCount;
    }

    /**
     * Sets the number of attendees for the item.
     *
     * @param attendeeCount the number of attendees for the item
     */
    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    /**
     * Returns the ID of the group the item belongs to.
     *
     * @return the ID of the group the item belongs to
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the ID of the group the item belongs to.
     *
     * @param groupId the ID of the group the item belongs to
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the capacity of the item.
     *
     * @return the capacity of the item
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the item.
     *
     * @param capacity the capacity of the item
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the status of the item.
     *
     * @return the status of the item
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the item.
     *
     * @param status the status of the item
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

package com.yallanow.analyticsservice.models;

/**
 * Represents a user in the system.
 */
public class User {
    private String userId;
    private String email;

    /**
     * Constructs a new User object with the specified user ID and email.
     *
     * @param userId the unique identifier for the user
     * @param email the email address of the user
     */
    public User(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // Getters and setters for all fields

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

package com.yallanow.analyticsservice.models;

public class User {
    private String userId;
    private String email;

    public User(String userId, String email) {
        this.userId = userId;

        this.email = email;
    }

    // Getters and setters for all fields

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

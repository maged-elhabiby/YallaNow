package com.yallanow.analyticsservice.models;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String email;
    private Integer age;
    private String gender;
    private List<String> interests;

    public User(String userId, String name, String email, Integer age, String gender, List<String> interests) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
    }

    // Getters and setters for all fields

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}

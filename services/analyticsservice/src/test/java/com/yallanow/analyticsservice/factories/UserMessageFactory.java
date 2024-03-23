package com.yallanow.analyticsservice.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.yallanow.analyticsservice.helpers.RandomNameGenerator.generateRandomUsername;

public class UserMessageFactory {

    public static Map<String, Object> generateUserMessage(String operationType) {
        if (!Objects.equals(operationType, "ADD") && !Objects.equals(operationType, "UPDATE") && !Objects.equals(operationType, "DELETE")) {
            throw new IllegalArgumentException("Invalid operation type.");
        }
        Map<String, Object> message = new HashMap<>();
        message.put("operation", operationType);
        Map<String, Object> eventDetails = generateRandomUser();
        message.put("user", eventDetails);
        return message;
    }

    private static Map<String, Object> generateRandomUser() {
        Random random = new Random();

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("userId", String.valueOf(random.nextInt(100000)));
        userDetails.put("username", generateRandomUsername());
        return userDetails;
    }

    public static class User {

        private final String userId;
        private final String username;

        public User(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public String getUserId() {
            return userId;
        }
        public String getUsername() {
            return username;
        }
    }
}

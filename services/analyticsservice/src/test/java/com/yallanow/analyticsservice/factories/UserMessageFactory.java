package com.yallanow.analyticsservice.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.yallanow.analyticsservice.helpers.RandomNameGenerator.generateRandomUsername;

public class UserMessageFactory {

    public static Map<String, Object> generateUserMessage(String operationType, int userId) {
        if (!Objects.equals(operationType, "ADD") && !Objects.equals(operationType, "UPDATE") && !Objects.equals(operationType, "DELETE")) {
            throw new IllegalArgumentException("Invalid operation type.");
        }
        Map<String, Object> pubSubMessage = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("operationType", operationType);
        pubSubMessage.put("attributes", attributes);

        String data = createUserData(operationType, userId);
        pubSubMessage.put("data", data);

        return pubSubMessage;
    }

    private static String createUserData(String operationType, int userId) {
        Map<String, Object> userDetails = generateRandomUserDetails(operationType, userId);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(userDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting event details to JSON", e);
        }
    }

    private static Map<String, Object> generateRandomUserDetails(String operationType, int userId) {
        Random random = new Random();

        Map<String, Object> userDetails = new HashMap<>();
        if (!"DELETE".equals(operationType)) {
        userDetails.put("userId", String.valueOf(random.nextInt(100000)));
        userDetails.put("email", generateRandomUsername() + "@gmail.com");
        } else {
            userDetails.put("userId", userId);
        }
        return userDetails;
    }

}

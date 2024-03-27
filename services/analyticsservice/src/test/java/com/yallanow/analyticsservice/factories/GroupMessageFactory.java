package com.yallanow.analyticsservice.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.pubsub.v1.PubsubMessage;
import com.yallanow.analyticsservice.helpers.RandomNameGenerator;
import com.google.protobuf.ByteString;

import java.text.SimpleDateFormat;
import java.util.*;

public class GroupMessageFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();



    public static PubsubMessage generateMessage(String operationType, int userId, int groupId) {
        if (!Objects.equals(operationType, "ADD") && !Objects.equals(operationType, "UPDATE") && !Objects.equals(operationType, "DELETE")) {
            throw new IllegalArgumentException("Invalid operation type.");
        }


        String groupData = createGroupData(userId, groupId);
        ByteString data = ByteString.copyFromUtf8(groupData);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(data)
                .putAttributes("operationType", operationType)
                .build();

        return pubsubMessage;
    }

    private static String createGroupData(int userId, int groupId) {
        Map<String, Object> eventDetails = generateRandomGroupDetails(userId, groupId);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(eventDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting event details to JSON", e);
        }
    }

    private static Map<String, Object> generateRandomGroupDetails(int userId, int groupId) {

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("groupId", groupId);
        data.put("role", "ADMIN");
        return data;
    }


}

package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.yallanow.analyticsservice.models.User;
import com.yallanow.analyticsservice.services.UserService;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class UserMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ItemMessageHandler.class);
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserConverter userConverter;

    @Autowired
    public UserMessageHandler(UserService userService, ObjectMapper objectMapper, UserConverter userConverter) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.userConverter = userConverter;
    }

    @ServiceActivator(inputChannel = "eventInputChannel")
    public void handleMessage(Message<String> message) {
        try {

            User user = userConverter.fromPubsubMessage(message.getPayload());
            String operationType = getOperationType(message);

            switch (operationType) {
                case "ADD":
                    userService.addUser(user);
                    break;
                case "UPDATE":
                    userService.updateUser(user);
                    break;
                case "DELETE":
                    userService.deleteUser(user);
                    break;
                default:
                    logger.error("Invalid operation type: {}", operationType);
            }
        } catch (IOException e) {
            logger.error("Error processing message: {}", e.getMessage());
        }
    }

    private String getOperationType(Message<String> message) throws JsonProcessingException {
        Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
        return (String) payload.get("operation");
    }
}

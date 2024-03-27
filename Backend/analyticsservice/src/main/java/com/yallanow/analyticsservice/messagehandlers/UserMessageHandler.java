package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.yallanow.analyticsservice.exceptions.UserServiceException;
import com.yallanow.analyticsservice.services.UserService;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

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

    @ServiceActivator(inputChannel = "userInputChannel")
    public void handleMessage(Message<?> message) {
        BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
        if (originalMessage == null) {
            throw new IllegalArgumentException("Message does not contain an AcknowledgeablePubsubMessage");
        }

        String payload = new String((byte[]) message.getPayload());
        try {

            @SuppressWarnings("unchecked")
            Map<String, Object> messageMap = objectMapper.readValue(payload, Map.class);
            String operationType = MessageHelper.getOperationType(messageMap);
            Map<String, Object> dataMap = MessageHelper.getData(messageMap);


            switch (operationType) {
                case "ADD":
                    userService.addUser(userConverter.getUserFromPubsubMessage(dataMap));
                    break;
                case "UPDATE":
                    userService.updateUser(userConverter.getUserFromPubsubMessage(dataMap));
                    break;
                case "DELETE":
                    userService.deleteUser(userConverter.getUserIdFromPubsubMessage(dataMap));
                    break;
                default:
                    logger.error("Invalid operation type: {}", operationType);
            }
            originalMessage.ack();
        } catch (UserServiceException e) {
            logger.error("Error processing item: {}", payload, e);
        } catch (Exception e) {
            logger.error("Unexpected error processing message: {}", payload, e);
        } finally {
            originalMessage.ack();
        }
    }
}

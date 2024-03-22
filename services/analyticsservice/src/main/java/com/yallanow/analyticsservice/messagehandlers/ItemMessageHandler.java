package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.services.ItemService;
import com.yallanow.analyticsservice.utils.ItemConverter;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;

@Component
public class ItemMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ItemMessageHandler.class);
    private final ItemService itemService;
    private final ObjectMapper objectMapper;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemMessageHandler(ItemService itemService, ObjectMapper objectMapper, ItemConverter itemConverter) {
        this.itemService = itemService;
        this.objectMapper = objectMapper;
        this.itemConverter = itemConverter;
    }

    @ServiceActivator(inputChannel = "eventInputChannel")
    public void handleMessage(Message<String> message) {
        try {
            Item item = itemConverter.fromPubsubMessage(message.getPayload());
            String operationType = getOperationType(message);

            switch (operationType) {
                case "ADD":
                    itemService.addItem(item);
                    break;
                case "UPDATE":
                    itemService.updateItem(item);
                    break;
                case "DELETE":
                    itemService.deleteItem(item);
                    break;
                default:
                    logger.error("Invalid operation type: {}", operationType);
            }
        } catch (ItemServiceException e) {
            logger.error("Error processing item: {}", message.getPayload(), e);
        } catch (Exception e) {
            logger.error("Unexpected error processing message: {}", message.getPayload(), e);
        }
    }

    private String getOperationType(Message<String> message) throws JsonProcessingException {
        Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
        return (String) payload.get("operation");
    }
}

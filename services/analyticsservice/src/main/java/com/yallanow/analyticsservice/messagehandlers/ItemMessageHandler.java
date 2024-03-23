package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import com.yallanow.analyticsservice.services.ItemService;
import com.yallanow.analyticsservice.utils.ItemConverter;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.util.Map;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;


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
    public void handleMessage(Message<?> message) {
        BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
        if (originalMessage == null) {
            throw new IllegalArgumentException("Message does not contain an AcknowledgeablePubsubMessage");
        }

        String payload = new String((byte[]) message.getPayload());
        try {
            // Deserialize the message payload to a Map
            Map<String, Object> messageMap = objectMapper.readValue(payload, Map.class);

            Map<String, String> attributes = (Map<String, String>) messageMap.get("attributes");
            String operationType = attributes.get("operationType");

            String eventDataJson = (String) messageMap.get("data");
            Map<String, Object> eventDataMap = objectMapper.readValue(eventDataJson, Map.class);

            switch (operationType) {
                case "ADD":
                    itemService.addItem(itemConverter.getItemFromPubsubMessage(eventDataMap));
                    break;
                case "UPDATE":
                    itemService.updateItem(itemConverter.getItemFromPubsubMessage(eventDataMap));
                    break;
                case "DELETE":
                    itemService.deleteItem(itemConverter.getIdFromPubSubMessage(eventDataMap));
                    break;
                default:
                    logger.error("Invalid operation type: {}", operationType);
            }
            originalMessage.ack();
        } catch (ItemServiceException e) {
            logger.error("Error processing item: {}", payload, e);
        } catch (Exception e) {
            logger.error("Unexpected error processing message: {}", payload, e);
        } finally {
            originalMessage.ack();
        }
    }
}


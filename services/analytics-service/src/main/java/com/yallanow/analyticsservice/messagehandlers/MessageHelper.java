package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * The MessageHelper class provides utility methods for handling messages.
 */
public class MessageHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Retrieves the operation type from the given message map.
     *
     * @param messageMap the message map containing the attributes
     * @return the operation type
     */
    public static String getOperationType(Map<String, Object> messageMap) {
        Map<String, String> attributes = (Map<String, String>) messageMap.get("attributes");
        return attributes.get("operationType");
    }

    /**
     * Retrieves the data from the given message map.
     *
     * @param messageMap the message map containing the data
     * @return the data as a map
     * @throws JsonProcessingException if there is an error processing the JSON data
     */
    public static Map<String, Object> getData(Map<String, Object> messageMap) throws JsonProcessingException {
        String dataJson = (String) messageMap.get("data");
        return objectMapper.readValue(dataJson, Map.class);
    }
}

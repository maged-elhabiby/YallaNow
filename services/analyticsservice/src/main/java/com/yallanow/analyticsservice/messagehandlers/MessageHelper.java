package com.yallanow.analyticsservice.messagehandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MessageHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getOperationType(Map<String, Object> messageMap) {
        Map<String, String> attributes = (Map<String, String>) messageMap.get("attributes");
        return attributes.get("operationType");
    }

    public static Map<String, Object> getData(Map<String, Object> messageMap) throws JsonProcessingException {
        String dataJson = (String) messageMap.get("data");
        return objectMapper.readValue(dataJson, Map.class);
    }
}

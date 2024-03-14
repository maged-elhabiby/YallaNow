package org.yallanow.feedservice.utils;

import org.yallanow.feedservice.configs.RecommendationConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.File;

public class ConfigLoader {
    public static RecommendationConfig loadConfig(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), RecommendationConfig.class);
    }
}

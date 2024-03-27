package org.yallanow.feedservice.utils;

import org.springframework.core.io.ClassPathResource;
import org.yallanow.feedservice.configs.RecommendationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ConfigLoader {

    public static RecommendationConfig loadConfig(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(resource.getInputStream(), RecommendationConfig.class);
    }
}
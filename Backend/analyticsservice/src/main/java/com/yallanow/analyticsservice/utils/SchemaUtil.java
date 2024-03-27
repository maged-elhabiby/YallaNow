package com.yallanow.analyticsservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SchemaUtil {

    private static final String RECOMBEE_ITEM_SCHEMA_PATH = "recombee_schemas/item_schema.json";
    private static final String RECOMBEE_USER_SCHEMA_PATH = "recombee_schemas/user_schema.json";

    private static final String PUBSUB_EVENT_SCHEMA_PATH = "pubsub_schemas/event_schema.json";
    private static final String PUBSUB_USER_SCHEMA_PATH = "pubsub_schemas/user_schema";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Optionally include a caching mechanism if schemas are static and read frequently

    public static Map<String, Object> loadRecombeeItemSchema() throws IOException {
        return loadSchema(RECOMBEE_ITEM_SCHEMA_PATH);
    }

    public static Map<String, Object> loadRecombeeUserSchema() throws IOException {
        return loadSchema(RECOMBEE_USER_SCHEMA_PATH);
    }

    /**
     * Loads a schema from the classpath and parses it into a Map.
     *
     * @param schemaPath The classpath location of the schema file.
     * @return The schema as a Map<String, Object>.
     * @throws IOException if the schema cannot be loaded or parsed.
     */
    public static Map<String, Object> loadSchema(String schemaPath) throws IOException {
        ClassPathResource resource = new ClassPathResource(schemaPath);
        String schemaJson = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()), StandardCharsets.UTF_8);
        return objectMapper.readValue(schemaJson, Map.class);
    }

}

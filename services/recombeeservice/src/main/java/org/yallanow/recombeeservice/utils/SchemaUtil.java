package org.yallanow.recombeeservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SchemaUtil {

    private static final String ITEM_SCHEMA_PATH = "recombee_schema/item_schema.json";
    private static final String USER_SCHEMA_PATH = "recombee_schema/user_schema.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Optionally include a caching mechanism if schemas are static and read frequently

    public static Map<String, Object> loadItemSchema() throws IOException {
        return loadSchema(ITEM_SCHEMA_PATH);
    }

    public static Map<String, Object> loadUserSchema() throws IOException {
        return loadSchema(USER_SCHEMA_PATH);
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

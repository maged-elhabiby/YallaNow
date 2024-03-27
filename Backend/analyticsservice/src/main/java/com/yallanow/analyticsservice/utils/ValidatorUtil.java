package com.yallanow.analyticsservice.utils;

import com.yallanow.analyticsservice.exceptions.ValidationException;

import java.io.IOException;
import java.util.Map;

public class ValidatorUtil {

    public static void validateItemProperties(Map<String, Object> itemProperties) throws ValidationException {
        Map<String, Object> userMap = loadSchema("item");
        validateSchema(userMap, itemProperties);
    }

    public static void validateUserProperties(Map<String, Object> userProperties) throws ValidationException {
        Map<String, Object> userMap = loadSchema("user");
        validateSchema(userMap, userProperties);
    }

    private static Map<String, Object> loadSchema(String schema) throws ValidationException {
        try {
            return switch (schema) {
                case ("item") -> SchemaUtil.loadRecombeeItemSchema();
                case ("user") -> SchemaUtil.loadRecombeeUserSchema();
                default -> throw new ValidationException("Invalid schema key");
            };
        } catch (IOException e) {
            throw new ValidationException("An error occurred reading the item schema", e);
        }
    }

    private static void validateSchema(Map<String, Object> schemaMap, Map<String, Object> properties) throws ValidationException {
        // Iterate through each key in the schemaMap to validate required properties and types
        for (Map.Entry<String, Object> entry : schemaMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> propertySchema = (Map<String, Object>) entry.getValue();

            // Check if the property is required
            boolean isRequired = (boolean) propertySchema.getOrDefault("required", false); // Assume false if not specified

            // Check for the existence of required properties
            if (isRequired && !properties.containsKey(key)) {
                throw new ValidationException("Missing required property: " + key);
            }

            // Proceed to type check only if the property exists
            if (properties.containsKey(key)) {
                // Example type check, assuming the schema defines types as simple class names (e.g., "String", "Integer")
                String expectedTypeName = (String) propertySchema.get("type");
                Object actualValue = properties.get(key);

                // This is a simplified type check. You'll need a more comprehensive approach for real use cases.
                if (!isTypeValid(expectedTypeName, actualValue)) {
                    throw new ValidationException("Type mismatch for property: " + key + ". Expected " + expectedTypeName + " but got " + (actualValue != null ? actualValue.getClass().getSimpleName() : "null"));
                }
            }
        }

        // Optionally, validate for any extra properties not defined in the schema
        for (String key : properties.keySet()) {
            if (!schemaMap.containsKey(key)) {
                throw new ValidationException("Undefined property: " + key);
            }
        }
    }

    private static boolean isTypeValid(String expectedTypeName, Object actualValue) {
        // Implement type validation logic here based on your application's requirements
        // This method should return true if the actualValue's type matches the expectedTypeName
        // Example basic check:
        return switch (expectedTypeName) {
            case "String" -> actualValue instanceof String;
            case "Integer" -> actualValue instanceof Integer;
            // Add cases for other types as needed
            default -> false; // Unknown type
        };
    }
}

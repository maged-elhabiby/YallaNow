package org.yallanow.feedservice.models;

import java.util.Map;

public class Recommendation {
    private String id;
    private Map<String, Object> properties; // Additional properties of the recommendation

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}

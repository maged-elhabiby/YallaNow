package com.yallanow.analyticsservice.config;

import com.recombee.api_client.util.Region;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Recombee service.
 * This class holds the configuration properties required to connect to the Recombee service.
 */
@Configuration
@ConfigurationProperties(prefix = "recombee")
public class RecombeeConfig {
    private String databaseId;
    private String secretToken;
    private Region region;

    /**
     * Get the database ID.
     * @return The database ID.
     */
    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * Set the database ID.
     * @param databaseId The database ID to set.
     */
    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    /**
     * Get the secret token.
     * @return The secret token.
     */
    public String getSecretToken() {
        return secretToken;
    }

    /**
     * Set the secret token.
     * @param secretToken The secret token to set.
     */
    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    /**
     * Get the region.
     * @return The region.
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Set the region.
     * @param region The region to set.
     */
    public void setRegion(Region region) {
        this.region = region;
    }
}

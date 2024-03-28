package org.yallanow.feedservice.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.recombee.api_client.util.Region;

@Configuration
@ConfigurationProperties(prefix = "recombee")
public class RecombeeConfig {
    private String databaseId;
    private String secretToken;
    private Region region;
    private boolean cascadeCreate;
    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean getCascadeCreate() {
        return cascadeCreate;
    }

    public void setCascadeCreate(boolean cascadeCreate) {
        this.cascadeCreate = cascadeCreate;
    }

}

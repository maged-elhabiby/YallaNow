package org.example.groups_microservice.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {
    private String authDns;

    public String getAuthDns(){
        return this.authDns;
    }
    public void setAuthDns(String authDns) {
        this.authDns = authDns;
    }
}

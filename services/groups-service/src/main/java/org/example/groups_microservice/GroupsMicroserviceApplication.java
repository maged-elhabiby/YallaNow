package org.example.groups_microservice;

import org.example.groups_microservice.ShutdownHook;
import org.example.groups_microservice.Service.GroupPubSub;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class GroupsMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupsMicroserviceApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    GroupPubSub groupPubService() throws IOException {
        String projectId = "yallanow-413400";
        String topicId = "group";
        GroupPubSub GroupPubSub = new GroupPubSub();
        GroupPubSub.initializePubSub(projectId, topicId);
        return GroupPubSub;
    }

    @Bean
    ShutdownHook shutdownHook( GroupPubSub groupPubSub) {
        return new ShutdownHook(groupPubSub);
    }

}

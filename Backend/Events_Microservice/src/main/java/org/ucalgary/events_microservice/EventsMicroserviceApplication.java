package org.ucalgary.events_microservice;

import java.io.IOException;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.ucalgary.events_microservice.Service.EventsPubService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class EventsMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsMicroserviceApplication.class, args);

        // EventsPubService eventsPubService = SpringApplication
        //         .run(EventsMicroserviceApplication.class, args)
        //         .getBean(EventsPubService.class);
        // try {
        //     eventsPubService.subscribeGroups();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    EventsPubService eventsPubService() throws IOException {
        String projectId = "yallanow-413400";
        String topicId = "event";
        EventsPubService eventsPubService = new EventsPubService(objectMapper(), restTemplate());
        eventsPubService.initializePubSub(projectId, topicId);
        return eventsPubService;
    }

    @Bean
    ShutdownHook shutdownHook(EventsPubService eventsPubService) {
        return new ShutdownHook(eventsPubService);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}

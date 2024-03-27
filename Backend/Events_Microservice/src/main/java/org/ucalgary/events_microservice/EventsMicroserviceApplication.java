package org.ucalgary.events_microservice;

import java.io.IOException;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.ucalgary.events_microservice.Service.EventsPubService;
import org.ucalgary.events_microservice.Service.GroupUsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableAsync // Enable asynchronous execution
@EnableScheduling
public class EventsMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsMicroserviceApplication.class, args);
        
    }

    /**
     * Bean for the EventsPubService
     * @param groupUsersService
     * @return
     * @throws IOException
     */
    @Bean
    EventsPubService eventsPubService(GroupUsersService groupUsersService) throws IOException {
        String projectId = "yallanow-413400";
        String topicId = "event";

        EventsPubService eventsPubService = new EventsPubService(objectMapper(), restTemplate(), groupUsersService);
         eventsPubService.initializePubSub(projectId, topicId);
        eventsPubService.subscribeGroups();
        return eventsPubService;
    }

    /**
     * Bean for the RestTemplate
     * @return
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Bean for the ObjectMapper
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * Bean for the GroupUsersService
     * @return
     */
    @Bean
    ShutdownHook shutdownHook(EventsPubService eventsPubService) {
        return new ShutdownHook(eventsPubService);
    }
}

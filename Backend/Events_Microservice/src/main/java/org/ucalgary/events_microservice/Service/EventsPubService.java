package org.ucalgary.events_microservice.Service;


import com.google.pubsub.v1.TopicName;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.PubsubMessage;

import org.ucalgary.events_microservice.DTO.PubEvent;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.protobuf.ByteString;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.List;

/**
 * Service class for publishing events to the Google Cloud Pub/Sub.
 * This class provides methods to publish events to the Pub/Sub.
 * Note that you might have to use the Google Cloud SDK to authenticate the service account.
 */
@Service
public class EventsPubService {
    private Publisher publisher;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public EventsPubService(ObjectMapper objectMapper, RestTemplate restTemplate) throws IOException {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * Initializes the Google Cloud Pub/Sub publisher.
     * @param projectId The project ID of the Google Cloud project.
     * @param topicId The topic ID of the Google Cloud Pub/Sub topic.
     * @throws IOException if an error occurs while initializing the publisher.
     */
    public void initializePubSub(String projectId, String topicId) throws IOException {
        TopicName topicName = TopicName.of(projectId, topicId);
        this.publisher = Publisher.newBuilder(topicName).build();
    }

    /**
     * Publishes a list of events to the Google Cloud Pub/Sub.
     * @param eventsList The list of events to publish.
     */
    public void publishEvents(List<EventsEntity> eventsList, String Operation) {
        try {
            for (EventsEntity event : eventsList) {
                PubEvent publishEvent = new PubEvent(event, restTemplate);
                String jsonMessage = objectMapper.writeValueAsString(publishEvent);
                ByteString data = ByteString.copyFromUtf8(jsonMessage);
                PubsubMessage pubsubMessage = null;

                pubsubMessage = PubsubMessage.newBuilder()
                        .setData(data)
                        .putAttributes("operationType",Operation)
                        .build();
            
                publisher.publish(pubsubMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Publishing Events: " + e);
        }
    }

    /**
     * Shuts down the Google Cloud Pub/Sub publisher.
     * @throws InterruptedException if the thread is interrupted while waiting for the publisher to shut down.
     */
    public void shutdown() throws InterruptedException {
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

}


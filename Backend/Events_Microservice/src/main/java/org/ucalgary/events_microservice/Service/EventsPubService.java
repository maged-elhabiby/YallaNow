package org.ucalgary.events_microservice.Service;

import org.springframework.context.annotation.Bean;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.TopicName;

import io.grpc.netty.shaded.io.netty.handler.timeout.TimeoutException;

import com.google.api.core.ApiFuture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    public EventsPubService(ObjectMapper objectMapper, RestTemplate restTemplate)
            throws IOException {
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
     * @throws InterruptedException if the thread is interrupted while waiting for the publish operation to complete.
     */
    public List<ApiFuture<String>> publishEvents(List<EventsEntity> eventsList)
            throws InterruptedException {
        List<ApiFuture<String>> messageIds = new ArrayList<>();
        try {
            for (EventsEntity event : eventsList) {
                String imageUrl = restTemplate.getForObject("http://localhost:8081/microservice/images/GetImage/" + event.getImageId(), String.class);
                String jsonMessage = objectMapper.writeValueAsString(event);
                ByteString data = ByteString.copyFromUtf8(jsonMessage);
                PubsubMessage pubsubMessage = null;
                if (imageUrl != null) {
                    pubsubMessage = PubsubMessage.newBuilder()
                            .setData(data)
                            .putAttributes("operationType","GET")
                            .putAttributes("imageUrl",imageUrl)
                            .build();
                }else{
                    pubsubMessage = PubsubMessage.newBuilder()
                            .setData(data)
                            .putAttributes("operationType","GET")
                            .build();
                }
                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
                messageIds.add(messageIdFuture);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Publishing Events: " + e);
        }
        return messageIds;
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


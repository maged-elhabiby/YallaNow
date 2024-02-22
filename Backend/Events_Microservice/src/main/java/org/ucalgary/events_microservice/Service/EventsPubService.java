package org.ucalgary.events_microservice.Service;

import org.ucalgary.events_microservice.Entity.EventsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.PubsubMessage;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.TopicName;
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
    private final Publisher publisher;
    private final ObjectMapper objectMapper;

    public EventsPubService(ObjectMapper objectMapper)
            throws IOException {
        this.objectMapper = objectMapper; // ObjectMapper is used to convert the EventsEntity to a JSON string.
        String projectId = "yallanow-413400";
        String topicId = "event";
        TopicName topicName = TopicName.of(projectId, topicId); // Create a topic name using the project ID and topic ID.
        this.publisher = Publisher.newBuilder(topicName).build(); // Create a publisher using the topic name.
    }

    /**
     * Publishes a list of events to the Google Cloud Pub/Sub.
     * @param eventsList The list of events to publish.
     * @throws InterruptedException if the thread is interrupted while waiting for the publish operation to complete.
     */
    public List<ApiFuture<String>> publishEvents(List<EventsEntity> eventsList)
            throws InterruptedException {

        List<ApiFuture<String>> messageIds = new ArrayList<>(); // A list to store the message IDs of the published events.
        try {
            for (EventsEntity event : eventsList) {
                String jsonMessage = objectMapper.writeValueAsString(event); // Convert the event to a JSON string.
                ByteString data = ByteString.copyFromUtf8(jsonMessage); // Convert the JSON string to a ByteString.
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build(); // Create a PubsubMessage using the ByteString.

                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage); // Publish the message to the Pub/Sub.
                messageIds.add(messageIdFuture);
            }
        }catch (Exception e) {
            throw new RuntimeException("Error Publishing Events: "+e);
        } finally {
            publisher.shutdown(); // Shutdown the publisher.
            publisher.awaitTermination(1, TimeUnit.MINUTES); // Wait for the publisher to terminate.
        }
        return messageIds;
    }
}


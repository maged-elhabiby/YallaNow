package org.ucalgary.events_service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucalgary.events_service.Entity.EventsEntity;
import org.ucalgary.events_service.DTO.PubEvent;
import com.google.pubsub.v1.TopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Service class for publishing events to the Google Cloud Pub/Sub.
 * This class provides methods to publish events to the Pub/Sub.
 * Note that you might have to use the Google Cloud SDK to authenticate the service account.
 */
@Service
public class EventsPubService {
    private static final Logger logger = LoggerFactory.getLogger(EventsPubService.class);
    private Publisher publisher;
    private Subscriber subscriber;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final GroupUsersService groupUsersService;

    public EventsPubService(ObjectMapper objectMapper, RestTemplate restTemplate, GroupUsersService groupUsersService) throws IOException {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.groupUsersService = groupUsersService;
    }

    /**
     * Initializes the Google Cloud Pub/Sub publisher.
     * @param projectId The project ID of the Google Cloud project.
     * @param topicId The topic ID of the Google Cloud Pub/Sub topic.
     * @throws IOException if the publisher cannot be initialized.
     */
    public void initializePubSub(String projectId, String topicId) throws IOException {
        TopicName topicName = TopicName.of(projectId, topicId);
        this.publisher = Publisher.newBuilder(topicName).build();
    }

    /**
     * Publishes an event to the Google Cloud Pub/Sub.
     * @param event The event entity to publish.
     * @param Operation The operation type to publish.
     */
    public void publishEvents(EventsEntity event, String Operation) throws RuntimeException {
        try {
            PubEvent publishEvent = new PubEvent(event, restTemplate);
            String jsonMessage = objectMapper.writeValueAsString(publishEvent);
            ByteString data = ByteString.copyFromUtf8(jsonMessage);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAttributes("operationType", Operation)
                    .build();

            publisher.publish(pubsubMessage);
            logger.info("Published event: {} with operation: {}", event.getEventId(), Operation);
        } catch (Exception e) {
            logger.error("Error publishing event: {}", e.getMessage(), e);
            throw new RuntimeException("Error Publishing Events: " + e);
        }
    }


    /**
     * Subscribes to the Google Cloud Pub/Sub topic for group updates.
     * @throws IOException if the subscriber cannot be initialized.
     */
    @PostConstruct
    public void subscribeGroups() throws IOException, RuntimeException {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of("yallanow-413400", "group-sub");

        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            String jsonData = message.getData().toStringUtf8();
            String operationType = message.getAttributesMap().get("operationType");
            try {
                JsonNode groupMember = objectMapper.readTree(jsonData);
                logger.info("Received message with operation: {}", operationType);

                switch (operationType) {
                    case "ADD": {
                        groupUsersService.addGroupUser(groupMember.get("groupId").asInt(), groupMember.get("userId").asText(), groupMember.get("role").asText());
                        break;
                    }
                    case "DELETE": {
                        groupUsersService.removeGroupUser(groupMember.get("groupId").asInt(), groupMember.get("userId").asText());
                        break;
                    }
                    case "UPDATE": {
                        groupUsersService.updateGroupUserRole(groupMember.get("groupId").asInt(), groupMember.get("userId").asText(), groupMember.get("role").asText());
                        break;
                    }
                    default: {
                        throw new RuntimeException("Invalid Operation Type");
                    }
                }
            } catch (IOException e) {
                logger.error("Error processing message: {}", e.getMessage(), e);
            } finally {
                consumer.ack();
                logger.info("Acknowledged message with operation: {}", operationType);
            }
        };

        subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.addListener(new Subscriber.Listener() {
            public void failed(Subscriber.State from, Throwable failure) {
                   logger.error("Subscriber failed: {}", failure.getMessage(), failure);
               }
           },
                MoreExecutors.directExecutor());
        subscriber.startAsync().awaitRunning();
        logger.info("Subscriber for groups started");
    }


    /**
     * Shuts down the Google Cloud Pub/Sub subscriber.
     */
    @PreDestroy
    public void stopSubscriber(){
        if(subscriber != null){
            subscriber.stopAsync().awaitTerminated();
        }
    }


    /**
     * Shuts down the Google Cloud Pub/Sub publisher.
     * @throws InterruptedException if the publisher cannot be shut down.
     */
    public void shutdown() throws InterruptedException {
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

}


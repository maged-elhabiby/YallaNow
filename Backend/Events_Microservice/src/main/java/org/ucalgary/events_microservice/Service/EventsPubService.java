package org.ucalgary.events_microservice.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.TopicName;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import org.ucalgary.events_microservice.DTO.PubEvent;
import org.ucalgary.events_microservice.Entity.EventsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Map;
import com.google.protobuf.ByteString;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;

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
     * @throws IOException if an error occurs while initializing the publisher.
     */
    public void initializePubSub(String projectId, String topicId) throws IOException {
        TopicName topicName = TopicName.of(projectId, topicId);
        this.publisher = Publisher.newBuilder(topicName).build();
    }

    /**
     * Publishes a list of events to the Google Cloud Pub/Sub.
     * @param event An Event that is to be published
     */
    public void publishEvents(EventsEntity event, String Operation) {
        try {
            PubEvent publishEvent = new PubEvent(event, restTemplate);
            String jsonMessage = objectMapper.writeValueAsString(publishEvent);
            ByteString data = ByteString.copyFromUtf8(jsonMessage);
            PubsubMessage pubsubMessage = null;

            pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAttributes("operationType",Operation)
                    .build();
        
            publisher.publish(pubsubMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error Publishing Events: " + e);
        }
    }

    public void subscribeGroups() throws IOException {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of("yallanow-413400", "group-sub");

        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            String jsonData = message.getData().toStringUtf8();
            try {
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                JsonNode groupMembers = jsonNode.get("groupMembers");
                String operationType = message.getAttributesMap().get("operationType");

                if (operationType.equals("CREATE")) {
                    for (JsonNode groupMember : groupMembers) {
                        groupUsersService.addGroupUser(groupMember.get("groupId").asInt(), groupMember.get("userId").asText(), groupMember.get("role").asText());
                    }
                } else if (operationType.equals("DELETE")) {
                    for (JsonNode groupMember : groupMembers) {
                        groupUsersService.removeGroupUser(groupMember.get("groupId").asInt(), groupMember.get("userId").asText());
                    }
                } else if (operationType.equals("UPDATE")) {
                    for (JsonNode groupMember : groupMembers) {
                        groupUsersService.updateGroupUserRole(groupMember.get("groupId").asInt(), groupMember.get("userId").asText(), groupMember.get("role").asText());
                    }
                }else{
                    throw new RuntimeException("Invalid Operation Type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            consumer.ack();
        };

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.addListener(
                        new Subscriber.Listener() {
                public void failed(Subscriber.State from, Throwable failure) {
                    System.err.println("Subscriber failed: " + failure);
                }
            },
            MoreExecutors.directExecutor()
        );
        subscriber.startAsync().awaitRunning();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Subscriber interrupted: " + e);
                Thread.currentThread().interrupt();
            }
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


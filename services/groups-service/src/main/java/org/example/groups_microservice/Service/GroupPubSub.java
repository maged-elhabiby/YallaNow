package org.example.groups_microservice.Service;

import org.example.groups_microservice.DTO.UserRole;
import org.example.groups_microservice.Entity.EventEntity;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.example.groups_microservice.Entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class GroupPubSub {
    private static Publisher publisher;
    private static final Logger logger = LoggerFactory.getLogger(GroupPubSub.class);

    public GroupPubSub() {
    }

    public static void initializePubSub(String projectId, String topicId) {
        try {
            logger.info("Initializing the publisher");
            TopicName topicName = TopicName.of(projectId, topicId);
            publisher = Publisher.newBuilder(topicName).build();
        } catch (IOException e) {
            logger.error("Error while initializing the publisher: {}", e.getMessage());
            throw new RuntimeException("Error while initializing the publisher: " + e.getMessage());
        }
    }


    /**
     * Publishes a message to the Pub/Sub topic
     *
     * @param group     The group to be published
     * @param operation  The operation that was performed on the group
     *
     */
    public static void publishGroup(GroupEntity group, String operation) {
        try {
            // Convert the group to JSON
            JSONObject groupJson = convertGroupToJson(group);

            // Convert the JSON to a byte string

            // Create a Pub/Sub message as a JSON object
            ByteString data = ByteString.copyFromUtf8(groupJson.toString());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data).putAttributes("operationType", operation)
                    .build();
            // Publishing the message
            ApiFuture<String> future = publisher.publish(pubsubMessage);
            // Asynchronously handling the publishing response
            ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
                @Override
                public void onSuccess(String messageId) {
                    System.out.println("Published message ID: " + messageId);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.err.println("Error publishing message: " + t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        } catch (Exception e) {
            throw new RuntimeException("Error while publishing the group: " + e.getMessage());
        }
    }
    public static void publishGroupByMember(GroupEntity group, String operation) {
        try {
            logger.info("Publishing the group: {}", group.getGroupID());
            for (GroupMemberEntity member : group.getGroupMembers()) {
                logger.info("Publishing the group member: {}", member.getUserID());
                publishGroupMember(member, operation);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while publishing the group: " + e.getMessage());
        }
    }



    /**
     * Publishes only group ID to the Pub/Sub topic
     *
     * @param groupID     The group to be published
     * @param operation  The operation that was performed on the group
     *
     */
    public static void publishGroupID(Integer groupID,String operation){
        try {
            logger.info("Publishing the group: {}", groupID);
            // Create a Pub/Sub message as a JSON object
            ByteString data = ByteString.copyFromUtf8(groupID.toString());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data).putAttributes("operationType", operation)
                    .build();
            // Publishing the message
            ApiFuture<String> future = publisher.publish(pubsubMessage);
            // Asynchronously handling the publishing response
            ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
                @Override
                public void onSuccess(String messageId) {
                    System.out.println("Published message ID: " + messageId);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.err.println("Error publishing message: " + t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        } catch (Exception e) {
            throw new RuntimeException("Error while publishing the group: " + e.getMessage());

        }
    }
    /**
     * publish a single group member to the Pub/Sub topic
     * @param groupMember - the group member to be published
     * @param operation - the operation that was performed on the group member
     *
     */
    public static void publishGroupMember(GroupMemberEntity groupMember, String operation) {
        try {
            // Convert the group member to JSON
            logger.info("Publishing the group member: {}", groupMember.getUserID());
            JSONObject groupMemberJson = new JSONObject();
            groupMemberJson.put("userId", groupMember.getUserID().toString());
            groupMemberJson.put("role", groupMember.getRole());
            groupMemberJson.put("groupId", groupMember.getGroup().getGroupID());

            // Convert the JSON to a byte string
            ByteString data = ByteString.copyFromUtf8(groupMemberJson.toString());

            // Create a Pub/Sub message as a JSON object
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data).putAttributes("operationType", operation)
                    .build();

            // Publishing the message
            ApiFuture<String> future = publisher.publish(pubsubMessage);

            // Asynchronously handling the publishing response
            ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
                @Override
                public void onSuccess(String messageId) {
                    System.out.println("Published message ID: " + messageId);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.err.println("Error publishing message: " + t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        } catch (Exception e) {
            throw new RuntimeException("Error while publishing the group member: " + e.getMessage());
        }
    }



    /**
     * Shuts down the publisher
     *
     * @throws Exception if the publisher cannot be shut down
     */
    public void shutdownPublisher() throws Exception {
        logger.info("Shutting down the publisher");
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }



    /**
     * covert group to json
     * @param group - the group to be converted
     * @return json
     */

    public static JSONObject convertGroupToJson(GroupEntity group) throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray membersArray = new JSONArray();


        json.put("groupID", group.getGroupID());
        json.put("groupName", group.getGroupName());
        json.put("isPrivate", group.getIsPrivate());

        for (GroupMemberEntity member : group.getGroupMembers()) {
            JSONObject memberJson = new JSONObject();
            memberJson.put("userId", member.getUserID());
            memberJson.put("userName", member.getUserName());
            memberJson.put("role", member.getRole());
            membersArray.put(memberJson);
        }
        json.put("groupMembers", membersArray);

        JSONArray eventsArray = new JSONArray();
        for (EventEntity event : group.getEvents()) {
            JSONObject eventJson = new JSONObject();
            eventJson.put("eventId", event.getEventID());
            eventJson.put("globalEventId", event.getGlobalEventID());
            eventsArray.put(eventJson);
        }
        json.put("events", eventsArray);

        return json;


    }

    public void shutdown() {
    }




}

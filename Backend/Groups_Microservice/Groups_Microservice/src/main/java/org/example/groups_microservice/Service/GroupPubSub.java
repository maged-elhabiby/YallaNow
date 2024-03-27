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


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GroupPubSub {
    private static Publisher publisher;

    public GroupPubSub() {
    }

    public static void initializePubSub(String projectId, String topicId) {
        try {
            TopicName topicName = TopicName.of(projectId, topicId);
            publisher = Publisher.newBuilder(topicName).build();
        } catch (IOException e) {
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
    public void publishGroup(GroupEntity group, String operation) {
        try {
            // Convert the group to JSON
            JSONObject groupJson = convertGroupToJson(group);
            //groupJson.put("operation", operation);

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

    /**
     * Shuts down the publisher
     *
     * @throws Exception
     */
    public void shutdownPublisher() throws Exception {
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

    /**
     * Main method to test the Pub/Sub functionality
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {

        String projectId = "yallanow-413400";
        String topicId = "group";

        initializePubSub(projectId, topicId);

    }


    /**
     * covert group to json
     * @param group
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
            memberJson.put("groupMemberId", member.getGroupMemberID());
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


    /**
     * Test the convertGroupToJson method
     *
     */
//    public static void main(String... args) throws Exception {
//        GroupEntity group = new GroupEntity();
//        GroupMemberEntity groupMember = new GroupMemberEntity();
//        UserRole role = UserRole.ADMIN;
//        EventEntity event = new EventEntity();
//        event.setEventID(1);
//        event.setGlobalEventID(1);
//        group.getEvents().add(event);
//        groupMember.setUserID(1);
//        groupMember.setUserName("User 1");
//        groupMember.setRole(role);
//        groupMember.setGroup(group);
//        group.getGroupMembers().add(groupMember);
//        group.setGroupID(1);
//        group.setGroupName("Group 1");
//        group.setIsPrivate(false);
//        group.setGroupMembers(group.getGroupMembers());
//        group.setEvents(group.getEvents());
//        System.out.println(convertGroupToJson(group));
//        }


}

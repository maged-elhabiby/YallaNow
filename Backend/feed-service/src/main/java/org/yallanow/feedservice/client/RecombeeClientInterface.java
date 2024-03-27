package org.yallanow.feedservice.client;

import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.bindings.RecommendationResponse;
import org.yallanow.feedservice.exceptions.RecombeeClientException;

import java.util.Map;

public interface RecombeeClientInterface {


    // If no user id given, then create user
    //      Therefore user id should be included in every request / response
    //      Then frontend can store the new user id if not signed in
    // Issue: it is not very clear that the feed service is creating the user id...
    // Issue - race condition: if the auth service creates a user, send the user id back to the frontend,
    //          and the frontend then immediately requests feed, then the user might not exist yet.
    //          This is because the users are updated though pubsub, which is async...
    // Consideration: If the user is created with cascade create, then they will eventually be updated when pubsub messages.
    // So then cascade create should always be set to true...
    // Then the service class need to detect if a userId is present of not...
    // Create UUID if not present.
    // Send back UUID from recombee.
    // Let frontend store as cookie....
    // Let frontend send id to auth in the case of unregistered feed strategy
    // Working with the idea of eventual consistency...


    RecommendationResponse recommendItemsToUser(String userId, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse recommendItemsToItem(String itemId, String targetItemId, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse recommendNextItems(String recommId, long count) throws RecombeeClientException;

    RecommendationResponse recommendItemSegmentsToUser(String userId, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse recommendItemSegmentsToItem(String itemId, String targetUserId, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse recommendUsersToUser(String userId, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse searchItems(String userId, String searchQuery, long count, String scenario) throws RecombeeClientException;

    RecommendationResponse searchItemSegments(String userId, String searchQuery, long count, String scenario) throws RecombeeClientException;

}

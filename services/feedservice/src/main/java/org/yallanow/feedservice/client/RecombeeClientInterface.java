package org.yallanow.feedservice.client;

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


    Map<String, Object> recommendItemsToUser() throws RecombeeClientException;

    Map<String, Object> recommendItemsToItems() throws RecombeeClientException;

    Map<String, Object> recommendNextItems() throws RecombeeClientException;

    Map<String, Object> recommendItemSegmentsToUser() throws RecombeeClientException;

    Map<String, Object> recommendItemSegmentsToItem() throws RecombeeClientException;

    // Map<String, Object> recommendItemSegmentsToItemSegments() throws RecombeeClientException;

    Map<String, Object> recommendUsersToUser() throws RecombeeClientException;

    // Map<String, Object> recommendUsersToItem() throws  RecombeeClientException;

    Map<String, Object> searchItems() throws RecombeeClientException;

    Map<String, Object> searchItemSegments() throws RecombeeClientException;

}

package org.yallanow.feedservice.client;

import org.yallanow.feedservice.exceptions.RecombeeClientException;

public interface RecombeeClientInterface {

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

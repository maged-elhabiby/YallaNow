package com.yallanow.analyticsservice.client;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.ApiException;
import com.yallanow.analyticsservice.config.RecombeeConfig;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementation of the RecombeeClient interface.
 * This class is responsible for initializing the RecombeeClient and providing access to its functionalities.
 */
@Component
public class RecombeeClientImpl implements RecombeeClientInterface {

    private final RecombeeClient client;

    /**
     * Implementation of the RecombeeClient interface.
     * This class is responsible for initializing the RecombeeClient and providing access to its functionalities.
     */
    @Autowired
    public RecombeeClientImpl(RecombeeConfig recombeeConfig) {
        try {
            this.client = new RecombeeClient(
                    recombeeConfig.getDatabaseId(),
                    recombeeConfig.getSecretToken()
            ).setRegion(recombeeConfig.getRegion());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RecombeeClient", e);
        }
    }

    /**
     * Adds an item to the Recombee client with the specified item ID and properties.
     *
     * @param itemId          the ID of the item to be added
     * @param itemProperties  a map containing the properties of the item
     * @throws RecombeeClientException if there is an error while adding the item
     */
    @Override
    public void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    /**
        * Updates the properties of an item in the Recombee database.
        *
        * @param itemId          the ID of the item to update
        * @param itemProperties  a map containing the updated properties of the item
        * @throws RecombeeClientException if there is an error while updating the item
        */
    @Override
    public void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    /**
     * Deletes an item from the Recombee client.
     *
     * @param itemId the ID of the item to be deleted
     * @throws RecombeeClientException if there is an error while deleting the item
     */
    @Override
    public void deleteItem(String itemId) throws RecombeeClientException {
        sendRequest(new DeleteItem(itemId));
    }

    /**
     * Adds a new user to the Recombee client with the specified user ID and properties.
     *
     * @param userId          the ID of the user to be added
     * @param userProperties  a map containing the properties of the user
     * @throws RecombeeClientException if there is an error while adding the user
     */
    @Override
    public void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException {
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(true));
    }

    /**
        * Updates the properties of a user in the Recombee client.
        *
        * @param userId          the ID of the user to update
        * @param userProperties  a map of user properties to update
        * @throws RecombeeClientException if there is an error updating the user
        */
    @Override
    public void updateUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException {
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(false)
        );
    }

    /**
     * Deletes a user from the Recombee client.
     *
     * @param userId the ID of the user to be deleted
     * @throws RecombeeClientException if there is an error while deleting the user
     */
    @Override
    public void deleteUser(String userId) throws RecombeeClientException {
        sendRequest(new DeleteUser(userId));
    }

    /**
     * Sets the properties for items in the analytics service.
     *
     * @throws RecombeeClientException if there is an error in the Recombee client.
     */
    @Override
    public void setItemProperties() throws RecombeeClientException {
        try {
            AddItemProperty[] propertyRequests = {
                    new AddItemProperty("groupId", "string"),
                    new AddItemProperty("eventTitle", "string"),
                    new AddItemProperty("eventDescription", "string"),
                    new AddItemProperty("eventStartTime", "timestamp"),
                    new AddItemProperty("eventEndTime", "timestamp"),
                    new AddItemProperty("eventLocationStreet", "string"),
                    new AddItemProperty("eventLocationCity", "string"),
                    new AddItemProperty("eventLocationProvince", "string"),
                    new AddItemProperty("eventLocationCountry", "string"),
                    new AddItemProperty("eventAttendeeCount", "int"),
                    new AddItemProperty("eventCapacity", "int"),
                    new AddItemProperty("eventStatus", "string"),
                    new AddItemProperty("eventImageUrl", "image")
            };

            Batch batch = new Batch(propertyRequests);
            client.send(batch);
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
    }

    /**
     * Sets the user properties in the Recombee client.
     *
     * @throws RecombeeClientException if there is an error in the Recombee client.
     */
    public void setUserProperties() throws RecombeeClientException {
        try {
            AddUserProperty[] propertyRequests = {
                    new AddUserProperty("username", "string"),
                    new AddUserProperty("email", "string"),
                    // new AddUserProperty("gender", "set"),
            };

            Batch batch = new Batch(propertyRequests);
            client.send(batch);
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
    }


    /**
     * Sends a request to the Recombee client.
     *
     * @param request the request to be sent
     * @throws RecombeeClientException if there is an error while sending the request
     */
    private void sendRequest(Request request) throws RecombeeClientException {
        try {
            client.send(request);
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
    }

    /**
     * Handles a generic ApiException by throwing a RecombeeClientException.
     * 
     * @param e The ApiException that occurred while communicating with Recombee.
     * @throws RecombeeClientException if an error occurs while communicating with Recombee.
     */
    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee" + e.getMessage(), e);
    }


}

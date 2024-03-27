package com.yallanow.analyticsservice.client;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.ApiException;
import com.yallanow.analyticsservice.config.RecombeeConfig;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RecombeeClientImpl implements RecombeeClientInterface {

    private final RecombeeClient client;

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

    @Override
    public void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void deleteItem(String itemId) throws RecombeeClientException {
        sendRequest(new DeleteItem(itemId));
    }

    @Override
    public void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException {
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void updateUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException {
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(false)
        );
    }

    @Override
    public void deleteUser(String userId) throws RecombeeClientException {
        sendRequest(new DeleteUser(userId));
    }

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


    private void sendRequest(Request request) throws RecombeeClientException {
        try {
            client.send(request);
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
    }

    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee" + e.getMessage(), e);
    }


}

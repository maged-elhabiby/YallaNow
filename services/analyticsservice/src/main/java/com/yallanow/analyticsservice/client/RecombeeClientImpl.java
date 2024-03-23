package com.yallanow.analyticsservice.client;

import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.config.RecombeeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yallanow.analyticsservice.exceptions.ValidationException;
import com.yallanow.analyticsservice.utils.ValidatorUtil;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.*;

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
    public void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException {
        validateItemProperties(itemProperties);
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void deleteItem(String itemId) throws RecombeeClientException {
        sendRequest(new DeleteItem(itemId));
    }

    @Override
    public Map<String, Object> getItem(String itemId) throws RecombeeClientException {
        try {
            return client.send(new GetItemValues(itemId));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException, ValidationException {
        validateUserProperties(userProperties);
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(true));
    }

    @Override
    public void updateUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException, ValidationException {
        validateUserProperties(userProperties);
        sendRequest(new SetUserValues(userId, userProperties)
                .setCascadeCreate(false)
        );
    }

    @Override
    public void deleteUser(String userId) throws RecombeeClientException {
        sendRequest(new DeleteUser(userId));
    }

    @Override
    public Map<String, Object> getUser(String userId) throws RecombeeClientException {
        try {
            return client.send(new GetUserValues(userId));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
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

    // Validation Logic
    private void validateItemProperties(Map<String, Object> itemProperties) throws ValidationException {
        ValidatorUtil.validateItemProperties(itemProperties);
    }

    private void validateUserProperties(Map<String, Object> userProperties) throws ValidationException {
        ValidatorUtil.validateUserProperties(userProperties);
    }

}

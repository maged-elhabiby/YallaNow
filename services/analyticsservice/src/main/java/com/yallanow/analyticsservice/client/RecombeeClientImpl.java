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
        this.client = new RecombeeClient(
                recombeeConfig.getDatabaseId(),
                recombeeConfig.getSecretToken()
        ).setRegion(recombeeConfig.getRegion());
    }

    @Override
    public void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException {
        validateItemProperties(itemProperties);
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
    public void addDetailView(String userId, String itemId, String recommId) throws RecombeeClientException {
        sendRequest(new AddDetailView(userId, itemId)
                .setCascadeCreate(false)
                .setRecommId(recommId)
        );
    }

    @Override
    public void deleteDetailView(String userId, String itemId) throws RecombeeClientException {
        sendRequest(new DeleteDetailView(userId, itemId));
    }

    @Override
    public void addPurchase(String userId, String itemId) throws RecombeeClientException {
        sendRequest(new AddPurchase(userId, itemId));
    }

    @Override
    public void deletePurchase(String userId, String itemId) throws RecombeeClientException {
        sendRequest(new DeletePurchase(userId, itemId));
    }


    private void sendRequest(Request request) throws RecombeeClientException {
        try {
            client.send(request);
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
    }

    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee", e);
    }

    // Validation Logic
    private void validateItemProperties(Map<String, Object> itemProperties) throws ValidationException {
        ValidatorUtil.validateItemProperties(itemProperties);
    }

    private void validateUserProperties(Map<String, Object> userProperties) throws ValidationException {
        ValidatorUtil.validateUserProperties(userProperties);
    }

}

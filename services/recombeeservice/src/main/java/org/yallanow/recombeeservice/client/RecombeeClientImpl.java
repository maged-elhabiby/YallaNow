package org.yallanow.recombeeservice.client;

import io.micrometer.core.instrument.config.validate.Validated;
import org.yallanow.recombeeservice.exceptions.RecombeeClientException;
import org.yallanow.recombeeservice.config.RecombeeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yallanow.recombeeservice.exceptions.ValidationException;
import org.yallanow.recombeeservice.utils.ValidatorUtil;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.*;
import com.recombee.api_client.bindings.*;


import java.util.List;
import java.util.Map;


@Component
public class RecombeeClientImpl implements RecombeeClientInterface {

    private final RecombeeConfiguration recombeeConfiguration;
    private final RecombeeClient client;

    @Autowired
    public RecombeeClientImpl(RecombeeConfiguration recombeeConfiguration) {
        this.recombeeConfiguration = recombeeConfiguration;
        this.client = new RecombeeClient(
                recombeeConfiguration.getDatabaseId(),
                recombeeConfiguration.getSecretToken()
        ).setRegion(recombeeConfiguration.getRegion());
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

    @Override
    public Map<String, Object> recommendItemsToUser(String userId, long count, String scenario, boolean returnProperties, String filter) {
        try {
            return client.send(new RecommendItemsToUser(userId, count)
                    .setScenario(scenario)
                    .setReturnProperties(returnProperties)
                    .setFilter(filter)
            );
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public void recommendItemsToItem(String itemId, String targetUserId, long count, String scenario, boolean returnProperties, String filter) {
        try {
            return client.send(new GetUserValues(userId));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public void recommendNextItems(String recommId, long count) {
        try {
            return client.send(new GetUserValues(userId));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public void searchItems(String userId, String searchQuery, long count, String scenario, boolean returnProperties, String filter) {

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

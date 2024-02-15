package org.yallanow.recombeeservice.client;

import org.yallanow.recombeeservice.exceptions.RecombeeClientException;
import org.yallanow.recombeeservice.config.RecombeeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.*;

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
    public void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        sendRequest(new SetItemValues(itemId, itemProperties)
                .setCascadeCreate(true)
        );
    }

    @Override
    public void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        // response = client.send( new );
    }

    @Override
    public void deleteItem(String itemId) throws RecombeeClientException {
        sendRequest(new DeleteItem(itemId));
    }

    @Override
    public Map<String, Object> getItem(String itemId) throws RecombeeClientException {
        return null;
    }

    private void sendRequest(Request request) throws RecombeeClientException {
        try {
            client.send(request);
        } catch (ApiException e) {
            throw new RecombeeClientException("err", e);
        }
    }


    private void handleResponseException(ResponseException e) {
        switch (e.getStatusCode()) {
            case 400:
                // Handle case where itemId does not match the expected pattern
                System.out.println("Error: The itemId format is invalid.");
                break;
            case 404:
                // Handle case where itemId is not present in the item catalog
                System.out.println("Note: The item was not present in the catalog, so nothing was deleted.");
                break;
            default:
                // Handle other unexpected statuses
                System.out.println("Unexpected error occurred: " + e.getMessage());
                break;
        }
    }

    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee", e);
    }
}

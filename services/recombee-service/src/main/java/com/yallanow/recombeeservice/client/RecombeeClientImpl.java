package com.yallanow.recombeeservice.client;

import com.yallanow.recombeeservice.exceptions.RecombeeClientException;
import com.yallanow.recombeeservice.config.RecombeeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.recombee.api_client.exceptions.ApiException;
import com.recombee.api_client.exceptions.ResponseException;

import java.util.List;
import java.util.Map;

@Component
public class RecombeeClientImpl implements RecombeeClient {

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
    public Item addItem(Map<String, Object> itemProperties) throws RecombeeClientException {
    }

    @Override
    public void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException {
        response = client.send( new );
    }

    @Override
    public void deleteItem(String itemId) throws R{
        response = client.send(new DeleteItem(itemId));
    }

    @Override
    public Map<String, Object> getItem(String itemId) throws RecombeeClientException {
        
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

    private void handleGenericApiException(ApiException e) {
        throw new RecombeeClientException("An error occurred while communicating with Recombee", e);
    }
}

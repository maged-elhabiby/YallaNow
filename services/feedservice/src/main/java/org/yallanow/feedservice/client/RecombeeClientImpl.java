package org.yallanow.feedservice.client;

import org.yallanow.feedservice.exceptions.RecombeeClientException;
import org.yallanow.feedservice.config.RecombeeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


}

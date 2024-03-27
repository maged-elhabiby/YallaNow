package org.yallanow.feedservice.client;

import com.recombee.api_client.bindings.RecommendationResponse;
import org.yallanow.feedservice.exceptions.RecombeeClientException;
import org.yallanow.feedservice.configs.RecombeeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.*;
import com.recombee.api_client.exceptions.*;

import java.util.Map;

@Component
public class RecombeeClientImpl implements RecombeeClientInterface {

    private final RecombeeClient client;
    private final boolean cascadeCreate;

    @Autowired
    public RecombeeClientImpl(RecombeeConfig recombeeConfig) {
        this.client = new RecombeeClient(
                recombeeConfig.getDatabaseId(),
                recombeeConfig.getSecretToken()
        ).setRegion(recombeeConfig.getRegion());
        cascadeCreate = recombeeConfig.getCascadeCreate();
    }

    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee", e);
    }


    @Override
    public RecommendationResponse recommendItemsToUser(String userId, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new RecommendItemsToUser(userId, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate)
                    .setReturnProperties(true)

            );

        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse recommendItemsToItem(String itemId, String targetItemId, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new RecommendItemsToItem(itemId, targetItemId, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse recommendNextItems(String recommId, long count) throws RecombeeClientException {
        try {
            return client.send(new RecommendNextItems(recommId, count));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse recommendItemSegmentsToUser(String userId, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new RecommendItemSegmentsToUser(userId, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse recommendItemSegmentsToItem(String itemId, String targetUserId, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new RecommendItemSegmentsToItem(itemId, targetUserId, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse recommendUsersToUser(String userId, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new RecommendUsersToUser(userId, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse searchItems(String userId, String searchQuery, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new SearchItems(userId, searchQuery, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate)
                    .setReturnProperties(true)
            );
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    @Override
    public RecommendationResponse searchItemSegments(String userId, String searchQuery, long count, String scenario) throws RecombeeClientException {
        try {
            return client.send(new SearchItemSegments(userId, searchQuery, count)
                    .setScenario(scenario)
                    .setCascadeCreate(cascadeCreate));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }


}

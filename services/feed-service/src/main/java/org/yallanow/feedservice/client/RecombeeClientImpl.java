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

    /**
     * Implementation of the RecombeeClient interface that connects to the Recombee service.
     * This class initializes the RecombeeClient with the provided configuration and sets the region.
     */
    @Autowired
    public RecombeeClientImpl(RecombeeConfig recombeeConfig) {
        this.client = new RecombeeClient(
                recombeeConfig.getDatabaseId(),
                recombeeConfig.getSecretToken()
        ).setRegion(recombeeConfig.getRegion());
        cascadeCreate = recombeeConfig.getCascadeCreate();
    }

    /**
     * Handles a generic ApiException by throwing a RecombeeClientException with a specific error message.
     *
     * @param e the ApiException that occurred while communicating with Recombee
     * @throws RecombeeClientException if an error occurs while communicating with Recombee
     */
    private void handleGenericApiException(ApiException e) throws RecombeeClientException {
        throw new RecombeeClientException("An error occurred while communicating with Recombee", e);
    }


    /**
     * Represents the response received from the recommendation service when recommending items to a user.
     * @param userId the user ID for which to recommend items
     * @param count the number of items to recommend
     * @param scenario the scenario for which to recommend items
     * @return the response received from the recommendation service
     */
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


    /**
     * Represents the response of a recommendation request for items.
     */
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

    /**
     * Represents the response received from the recommendation service.
     * It contains the recommended items based on the given recommendation ID and count.
     */
    @Override
    public RecommendationResponse recommendNextItems(String recommId, long count) throws RecombeeClientException {
        try {
            return client.send(new RecommendNextItems(recommId, count));
        } catch (ApiException e) {
            handleGenericApiException(e);
        }
        return null;
    }

    /**
     * Recommends item segments to a user based on their preferences and the specified scenario.
     *
     * @param userId   the ID of the user
     * @param count    the number of item segments to recommend
     * @param scenario the scenario for recommendation
     * @return a RecommendationResponse object containing the recommended item segments
     * @throws RecombeeClientException if there is an error in the Recombee client
     */
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

    /**
     * Represents the response received from the recommendation service when recommending item segments to an item.
     * This response contains the recommended item segments for the specified item and target user.
     */
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

    /**
     * Represents the response received from the recommendation service when recommending users to a user.
     * @param userId the user ID for which to recommend users
     * @param count the number of users to recommend
     * @param scenario the scenario for which to recommend users
     * @return the response received from the recommendation service
     * @throws RecombeeClientException if there is an error with the Recombee client
     * @throws ApiException if there is an error with the Recombee API
     */
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

    /**
     * Represents the response received from the recommendation service.
     * It contains the recommended items based on the search query and user ID.
     * @param userId the user ID for which to recommend items
     * @param searchQuery the search query
     * @param count the number of items to recommend
     * @param scenario the scenario for which to recommend items
     * @return the response received from the recommendation service
     * @throws RecombeeClientException if there is an error with the Recombee client
     * @throws ApiException if there is an error with the Recombee API
     */
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

    /**
        * Searches for item segments based on the given parameters.
        *
        * @param userId      the ID of the user
        * @param searchQuery the search query
        * @param count       the number of item segments to retrieve
        * @param scenario    the scenario for the search
        * @return the response received from the recommendation service
        * @throws RecombeeClientException if there is an error with the Recombee client
        */
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

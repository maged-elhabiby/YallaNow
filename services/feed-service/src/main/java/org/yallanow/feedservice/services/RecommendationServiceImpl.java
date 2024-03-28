package org.yallanow.feedservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yallanow.feedservice.client.RecombeeClientImpl;
import org.yallanow.feedservice.client.RecombeeClientInterface;
import org.yallanow.feedservice.configs.RecommendationConfig;
import org.yallanow.feedservice.exceptions.RecombeeClientException;
import org.yallanow.feedservice.exceptions.RecommendationException;
import org.yallanow.feedservice.models.RecommendationRequest;
import org.yallanow.feedservice.models.RecommendationResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yallanow.feedservice.utils.ConfigLoader;

import java.io.IOException;

import static org.yallanow.feedservice.utils.RecombeeUtil.convertToRecommendationResponse;

/**
 * This class implements the RecommendationService interface and provides the implementation for various recommendation types.
 * It interacts with the RecombeeClientInterface to retrieve recommendations from the Recombee service.
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    private final RecommendationConfig recommendationConfig;
    private final RecombeeClientInterface recombeeClient;

    @Autowired
    RecommendationServiceImpl(RecombeeClientImpl recombeeClient) throws IOException {
        this.recombeeClient = recombeeClient;
        this.recommendationConfig = ConfigLoader.loadConfig("recommendation-config.json");
    }

    /**
     * Getter that sorts the enum values of the recommendation types.
     */
    @Override
    public RecommendationResponse getRecommendations(RecommendationRequest request) throws RecommendationException {
        return switch (request.getRecommendationType()) {
            case ItemsToUser -> recommendItemsToUser(request);
            case ItemsToItem -> recommendItemsToItem(request);
            case NextItems -> recommendNextItems(request);
            case ItemSegmentsToUser -> recommendItemSegmentsToUser(request);
            case ItemSegmentsToItem -> recommendItemSegmentsToItem(request);
            case UsersToUser -> recommendUsersToUser(request);
            case SearchItems -> searchItems(request);
            case SearchItemSegments -> searchItemSegments(request);
            default -> throw new RecommendationException("Unprocessable entity: invalid recommendation type", 422);
        };
    }


    /**
     * recommend items to a user based on the provided request.
     * @param request the recommendation request containing the necessary information.
     * @return a RecommendationResponse containing the recommended items.
     * @throws RecommendationException if an error occurs while getting the recommendations.
     * @throws RecombeeClientException if an error occurs while communicating with Recombee.
     */
    @Override
    public RecommendationResponse recommendItemsToUser(RecommendationRequest request) throws RecommendationException {
        validateScenario("ItemsToUser", request);
        if (request.getCount() <= 0) {
            throw new RecommendationException("Unprocessable entity: count must be greater than 0.", 422);
        }
        if (request.getUserId() == null) {
            throw new RecommendationException("Unprocessable entity: userId must be defined.", 422);
        }

        try {
            // Get recommendations from Recombee
            com.recombee.api_client.bindings.RecommendationResponse recombeeResponse = recombeeClient.recommendItemsToUser(
                    request.getUserId(), request.getCount(), request.getScenario()
            );

            // Convert Recombee response to RecommendationResponse
            RecommendationResponse response = convertToRecommendationResponse(recombeeResponse);

            // Set additional information in the response
            response.setUserId(request.getUserId());

            return response;
        } catch (RecombeeClientException e) {
            logger.error("Error getting item to user recommendations", e);
            throw new RecommendationException("Internal server error: recombeeClientException", e, 500);
        }
    }


    /**
     * Not implemented.
     */
    @Override
    public RecommendationResponse recommendItemsToItem(RecommendationRequest request) throws RecommendationException {
        validateScenario("ItemsToItem", request);

        throw new RecommendationException("Not implemented", 501);
    }

    /**
     * reccomending the next items to a user based on the provided request.
     * @param request the recommendation request containing the necessary information.
     * @return a RecommendationResponse containing the recommended items.
     * @throws RecommendationException if an error occurs while getting the recommendations.
     * @throws RecombeeClientException if an error occurs while communicating with Recombee.
     */
    @Override
    public RecommendationResponse recommendNextItems(RecommendationRequest request) throws RecommendationException {
        if (request.getCount() <= 0) {
            throw new RecommendationException("Unprocessable entity: count must be greater than 0.", 422);
        }
        if (request.getRecommId() == null) {
            throw new RecommendationException("Unprocessable entity: recommId must be defined.", 422);
        }

        try {
            // Get recommendations from Recombee
            com.recombee.api_client.bindings.RecommendationResponse recombeeResponse = recombeeClient.recommendNextItems(
                    request.getRecommId(), request.getCount()
            );

            // Convert Recombee response to RecommendationResponse
            return convertToRecommendationResponse(recombeeResponse);
        } catch (RecombeeClientException e) {
            logger.error("Error getting item to user recommendations", e);
            throw new RecommendationException("Internal server error: recombeeClientException", e, 500);
        }
    }

    /**
     * Not implemented yet.
     */
    @Override
    public RecommendationResponse recommendItemSegmentsToUser(RecommendationRequest request) throws RecommendationException {
        validateScenario("ItemSegmentsToUser", request);

        throw new RecommendationException("Not implemented", 501);
    }

    /**
     * Not implemented yet.
     */
    @Override
    public RecommendationResponse recommendItemSegmentsToItem(RecommendationRequest request) throws RecommendationException {
        validateScenario("ItemSegmentsToItem", request);

        throw new RecommendationException("Not implemented", 501);
    }

    /**
     * Not implemented yet.
     */
    @Override
    public RecommendationResponse recommendUsersToUser(RecommendationRequest request) throws RecommendationException {
        validateScenario("UsersToUser", request);

        throw new RecommendationException("Not implemented", 501);
    }

    /**
     * Searching for items based on the provided request.
     * @param request the recommendation request containing the necessary information.
     * @return a RecommendationResponse containing the recommended items.
     * @throws RecommendationException if an error occurs while getting the recommendations.
     * @throws RecombeeClientException if an error occurs while communicating with Recombee.
     */
    @Override
    public RecommendationResponse searchItems(RecommendationRequest request) throws RecommendationException {
        validateScenario("SearchItems", request);
        validateSearchRequest(request);

        try {
            // Get recommendations from Recombee
            com.recombee.api_client.bindings.RecommendationResponse recombeeResponse = recombeeClient.searchItems(
                    request.getUserId(), request.getSearchQuery(), request.getCount(), request.getScenario()
            );

            // Convert Recombee response to RecommendationResponse
            RecommendationResponse response = convertToRecommendationResponse(recombeeResponse);

            // Set additional information in the response
            response.setUserId(request.getUserId());

            return response;
        } catch (RecombeeClientException e) {
            logger.error("Error getting search item recommendations", e);
            throw new RecommendationException("Internal server error: recombeeClientException", e, 500);
        }
    }

    /**
     * Not implemented yet.
     */
    @Override
    public RecommendationResponse searchItemSegments(RecommendationRequest request) throws RecommendationException {
        validateScenario("SearchItemSegments", request);
        validateSearchRequest(request);

        throw new RecommendationException("Not implemented", 501);
    }

    /**
     * helper method to validate the scenario for the recommendation type.
     * @param recommendationType
     * @param request
     * @throws RecommendationException
     */
    private void validateScenario(String recommendationType, RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get(recommendationType).contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type " + recommendationType, 422);
        }
    }

    /**
     * helper method to validate the search request.
     * @param request
     * @throws RecommendationException
     */
    private void validateSearchRequest(RecommendationRequest request) throws RecommendationException {
        if (request.getCount() <= 0) {
            throw new RecommendationException("Unprocessable entity: count must be greater than 0.", 422);
        }
        if (request.getUserId() == null) {
            throw new RecommendationException("Unprocessable entity: userId must be defined.", 422);
        }
        if (request.getSearchQuery() == null) {
            throw new RecommendationException("Unprocessable entity: searchQuery must be defined.", 422);
        }
    }

}

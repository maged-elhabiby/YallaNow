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

    @Override
    public RecommendationResponse getRecommendations(RecommendationRequest request) throws RecommendationException {
        return switch (request.getRecommendationType()) {
            case ItemsToUser -> recommendItemsToUser(request);
            case ItemsToItem -> recommendItemsToItem(request);
            case NextItems -> recommendNextItems(request);
            case ItemSegmentsToUser -> recommendItemSegmentsToUser(request);
            case ItemSegmentsToItem -> recommendItemSegmentsToItem(request);
            case UsersToUser -> recommendUsersToUser(request);
            default -> throw new RecommendationException("Unprocessable entity: invalid recommendation type", 422);
        };
    }

    @Override
    public RecommendationResponse recommendItemsToUser(RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get("ItemsToUser").contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type ItemsToUser", 422);
        }
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


    @Override
    public RecommendationResponse recommendItemsToItem(RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get("ItemsToItem").contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type ItemsToItem", 422);
        }

        throw new RecommendationException("Not implemented", 501);
    }

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

    @Override
    public RecommendationResponse recommendItemSegmentsToUser(RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get("ItemSegmentsToUser").contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type ItemSegmentsToUser", 422);
        }

        throw new RecommendationException("Not implemented", 501);
    }

    @Override
    public RecommendationResponse recommendItemSegmentsToItem(RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get("ItemSegmentsToItem").contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type ItemSegmentsToItem", 422);
        }

        throw new RecommendationException("Not implemented", 501);
    }

    @Override
    public RecommendationResponse recommendUsersToUser(RecommendationRequest request) throws RecommendationException {
        if (!recommendationConfig.getRecommendationScenarios().get("UsersToUser").contains(request.getScenario())) {
            throw new RecommendationException("Invalid scenario for recommendation type UsersToUser", 422);
        }

        throw new RecommendationException("Not implemented", 501);
    }

}

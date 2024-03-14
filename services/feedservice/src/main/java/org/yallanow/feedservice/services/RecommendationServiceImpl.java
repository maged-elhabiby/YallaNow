package org.yallanow.feedservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yallanow.feedservice.client.RecombeeClientImpl;
import org.yallanow.feedservice.client.RecombeeClientInterface;
import org.yallanow.feedservice.configs.RecombeeConfig;
import org.yallanow.feedservice.exceptions.RecombeeClientException;
import org.yallanow.feedservice.exceptions.RecommendationException;
import org.yallanow.feedservice.models.RecommendationRequest;
import org.yallanow.feedservice.models.RecommendationResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.yallanow.feedservice.utils.RecombeeUtil.convertToRecommendationResponse;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    private final RecombeeClientInterface recombeeClient;
    private final boolean recombeeCascadeCreate;

    @Autowired
    RecommendationServiceImpl(RecombeeClientImpl recombeeClient, RecombeeConfig recombeeConfig) {
        this.recombeeClient = recombeeClient;
        this.recombeeCascadeCreate = recombeeConfig.getCascadeCreate();
    }

    @Override
    public RecommendationResponse getRecommendations(RecommendationRequest request) throws RecommendationException {
        switch (request.getRecommendationType()) {
            case ItemsToUser: {
                return recommendItemsToUser(request);
            }
            case ItemsToItem: {
                return recommendItemsToItem(request);
            }
            case NextItems: {
                return recommendNextItems(request);
            }
            case ItemSegmentsToUser: {
                return recommendItemSegmentsToUser(request);
            }
            case ItemSegmentsToItem: {
                return recommendItemSegmentsToItem(request);
            }
            case UsersToUser: {
                return recommendUsersToUser(request);
            }
            default: {
                throw new RecommendationException("Unprocessable entity: invalid recommendation type", 422);
            }
        }
    }

    @Override
    public RecommendationResponse recommendItemsToUser(RecommendationRequest request) throws RecommendationException {
        if (request.getCount() <= 0) {
            throw new RecommendationException("Unprocessable entity: count must be greater than 0.", 422);
        }
        if (request.getScenario() == null || request.getScenario().isEmpty()) {
            throw new RecommendationException("Unprocessable entity: scenario must be defined.", 422);
        }

        // Check if userId is included, generate a new one if not
        String userId = request.getUserId();
        boolean newUserCreated = false;

        if (userId == null) {
            if (recombeeCascadeCreate) {
                userId = String.valueOf(UUID.randomUUID());
                newUserCreated = true;
            } else {
                throw new RecommendationException("Unprocessable entity: userId must be defined.", 422);
            }
        }

        try {
            // Get recommendations from Recombee
            com.recombee.api_client.bindings.RecommendationResponse recombeeResponse = recombeeClient.recommendItemsToUser(
                    userId, request.getCount(), request.getScenario()
            );

            // Convert Recombee response to RecommendationResponse
            RecommendationResponse response = convertToRecommendationResponse(recombeeResponse);

            // Set additional information in the response
            response.setUserId(userId);
            response.setNewUserCreated(newUserCreated);

            return response;
        } catch (RecombeeClientException e) {
            logger.error("Error getting item to user recommendations", e);
            throw new RecommendationException("Internal server error: recombeeClientException", e, 500);
        }
    }


    @Override
    public RecommendationResponse recommendItemsToItem(RecommendationRequest request) throws RecommendationException {
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
            RecommendationResponse response = convertToRecommendationResponse(recombeeResponse);

            return response;
        } catch (RecombeeClientException e) {
            logger.error("Error getting item to user recommendations", e);
            throw new RecommendationException("Internal server error: recombeeClientException", e, 500);
        }
    }

    @Override
    public RecommendationResponse recommendItemSegmentsToUser(RecommendationRequest request) throws RecommendationException {
        throw new RecommendationException("Not implemented", 501);
    }

    @Override
    public RecommendationResponse recommendItemSegmentsToItem(RecommendationRequest request) throws RecommendationException {
        throw new RecommendationException("Not implemented", 501);
    }

    @Override
    public RecommendationResponse recommendUsersToUser(RecommendationRequest request) throws RecommendationException {
        throw new RecommendationException("Not implemented", 501);
    }

}

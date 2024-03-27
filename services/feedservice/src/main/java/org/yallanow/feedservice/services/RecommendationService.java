package org.yallanow.feedservice.services;

import org.yallanow.feedservice.exceptions.RecommendationException;
import org.yallanow.feedservice.models.RecommendationRequest;
import org.yallanow.feedservice.models.RecommendationResponse;

public interface RecommendationService {

    public RecommendationResponse getRecommendations(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendItemsToUser(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendItemsToItem(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendNextItems(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendItemSegmentsToUser(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendItemSegmentsToItem(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse recommendUsersToUser(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse searchItems(RecommendationRequest request) throws RecommendationException;

    public RecommendationResponse searchItemSegments(RecommendationRequest request) throws RecommendationException;

}
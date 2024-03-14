package org.yallanow.feedservice.utils;

import org.yallanow.feedservice.models.Recommendation;
import org.yallanow.feedservice.models.RecommendationResponse;

import java.util.ArrayList;
import java.util.List;

public class RecombeeUtil {

    public static RecommendationResponse convertToRecommendationResponse(com.recombee.api_client.bindings.RecommendationResponse recombeeResponse) {
        String recommId = recombeeResponse.getRecommId();
        List<Recommendation> recommendations = new ArrayList<>();

        for (com.recombee.api_client.bindings.Recommendation recombeeRecommendation : recombeeResponse.getRecomms()) {
            Recommendation recommendation = new Recommendation();
            recommendation.setId(recombeeRecommendation.getId());

            recommendations.add(recommendation);
        }

        RecommendationResponse response = new RecommendationResponse();
        response.setRecommId(recommId);
        response.setRecommendations(recommendations);

        return response;
    }
}

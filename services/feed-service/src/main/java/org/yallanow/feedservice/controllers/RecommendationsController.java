package org.yallanow.feedservice.controllers;

import org.yallanow.feedservice.exceptions.RecommendationException;
import org.yallanow.feedservice.models.Recommendation;
import org.yallanow.feedservice.models.RecommendationRequest;
import org.yallanow.feedservice.models.RecommendationResponse;
import org.yallanow.feedservice.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yallanow.feedservice.services.RecommendationServiceImpl;

@RestController
@RequestMapping("/recommendations")
@CrossOrigin
public class RecommendationsController {

    private final RecommendationService recommendationService;

    /**
     * The RecommendationsController class is responsible for handling HTTP requests related to recommendations.
     * It acts as a controller for the recommendation service.
     */
    @Autowired
    public RecommendationsController(RecommendationServiceImpl recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Retrieves recommendations based on the provided request.
     * 
     * @param request the recommendation request containing the necessary information.
     * @return a ResponseEntity containing the recommendation response.
     */
    @PostMapping
    public ResponseEntity<?> getRecommendations(@RequestBody RecommendationRequest request) {
        try {
            RecommendationResponse response = recommendationService.getRecommendations(request);
            return ResponseEntity.ok(response);
        } catch (RecommendationException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

}

package org.yallanow.feedservice.configs;

import java.util.List;
import java.util.Map;

public class RecommendationConfig {
    private Map<String, List<String>> recommendationScenarios;

    public Map<String, List<String>> getRecommendationScenarios() {
        return recommendationScenarios;
    }

    public void setRecommendationScenarios(Map<String, List<String>> recommendationScenarios) {
        this.recommendationScenarios = recommendationScenarios;
    }
}

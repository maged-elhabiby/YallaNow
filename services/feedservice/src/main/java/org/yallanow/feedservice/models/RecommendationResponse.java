package org.yallanow.feedservice.models;

import java.util.List;

public class RecommendationResponse {

    private String userId = null;
    private String itemId = null;
    private String recommId = null;
    private boolean newUserCreated = false;
    private List<Recommendation> recommendations;

    // Getters and setters
    public String getRecommId() {
        return recommId;
    }

    public void setRecommId(String recommId) {
        this.recommId = recommId;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public boolean isNewUserCreated() {
        return newUserCreated;
    }

    public void setNewUserCreated(boolean newUserCreated) {
        this.newUserCreated = newUserCreated;
    }
}

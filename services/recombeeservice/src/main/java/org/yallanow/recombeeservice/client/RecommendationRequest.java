package org.yallanow.recombeeservice.client;

public class RecommendationRequest {
    private final String userId;
    private final long count;
    private String scenario;
    private boolean cascadeCreate;
    private boolean returnProperties;
    private String[] includedProperties;
    private String filter;
    private String booster;
    private Logic logic;
    private String minRelevance;
    private double rotationRate;
    private double rotationTime;

    private RecommendationRequest(Builder builder) {
        this.userId = builder.userId;
        this.count = builder.count;
        this.scenario = builder.scenario;
        this.cascadeCreate = builder.cascadeCreate;
        this.returnProperties = builder.returnProperties;
        this.includedProperties = builder.includedProperties;
        this.filter = builder.filter;
        this.booster = builder.booster;
        this.logic = builder.logic;
        this.minRelevance = builder.minRelevance;
        this.rotationRate = builder.rotationRate;
        this.rotationTime = builder.rotationTime;
    }

    // Getters for all fields

    public static class Builder {
        private final String userId;
        private final long count;
        private String scenario;
        private boolean cascadeCreate = false; // Default value
        private boolean returnProperties = false; // Default value
        private String[] includedProperties;
        private String filter;
        private String booster;
        private Logic logic;
        private String minRelevance;
        private double rotationRate;
        private double rotationTime;

        public Builder(String userId, long count) {
            this.userId = userId;
            this.count = count;
        }

        public Builder setScenario(String scenario) {
            this.scenario = scenario;
            return this;
        }

        public Builder setCascadeCreate(boolean cascadeCreate) {
            this.cascadeCreate = cascadeCreate;
            return this;
        }

        // Setters for other fields, returning 'this' for chaining

        public RecommendationRequest build() {
            return new RecommendationRequest(this);
        }
    }
}

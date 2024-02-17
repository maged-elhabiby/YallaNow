package org.yallanow.recombeeservice.client;

public class RecommendationBuilder {
    private RecommendationType type;
    private String userId;
    private String itemId;
    private String targetUserId;
    private String recommId;
    private long count;
    private String scenario;
    private boolean returnProperties;
    private String filter;

    // Enum to represent different recommendation types
    public enum RecommendationType {
        ITEMS_TO_USER, ITEMS_TO_ITEM, NEXT_ITEMS
    }

    // Constructors, setters, and build() method

    public RecommendationRequest build() {
        return switch (type) {
            case ITEMS_TO_USER -> new RecommendItemsToUserRequest(userId, count, scenario, returnProperties, filter);
            case ITEMS_TO_ITEM ->
                    new RecommendItemsToItemRequest(itemId, targetUserId, count, scenario, returnProperties, filter);
            case NEXT_ITEMS -> new RecommendNextItemsRequest(recommId, count);
            default -> throw new IllegalStateException("Unexpected recommendation type: " + type);
        };
    }
}

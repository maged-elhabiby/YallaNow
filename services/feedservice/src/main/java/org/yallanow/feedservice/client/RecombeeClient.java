package org.yallanow.feedservice.client;

import java.util.Map;

public interface RecombeeClient {

    // Rotation rate, Booster, 
    Map<String, Object> recommendItemsToUser(
            String userId, long count, String scenario);



}

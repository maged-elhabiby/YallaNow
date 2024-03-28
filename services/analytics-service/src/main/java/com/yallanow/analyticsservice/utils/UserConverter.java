package com.yallanow.analyticsservice.utils;

import com.yallanow.analyticsservice.models.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The UserConverter class provides methods to convert User objects to different formats and vice versa.
 * It is used in the analytics service of the YallaNow application.
 */
@Component
public class UserConverter {

    /**
     * Converts a User object to a map representation compatible with Recombee.
     *
     * @param user the User object to be converted
     * @return a map containing the converted user data
     */
    public Map<String, Object> convertUserToRecombeeMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());

        return map;
    }

    /**
     * Converts a map of user data to a string representation of the user ID.
     *
     * @param map the map containing the user data
     * @return the string representation of the user ID
     * @throws IllegalArgumentException if the 'userId' key is missing in the user data map
     */
    public String getUserIdFromPubsubMessage(Map<String, Object> map) {
        return String.valueOf(Optional.ofNullable(map.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'userId' in user data")));
    }


    public User getUserFromPubsubMessage(Map<String, Object> map) {

        String userId = String.valueOf(Optional.ofNullable(map.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'userId' in data")));

        String email = Optional.ofNullable((String) map.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'email' in data"));

        return new User(userId, email);
    }

}

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

    /**
     * Represents a user.
     */
    public class User {
        private String userId;
        private String email;

        /**
         * Constructs a new User object with the specified user ID and email.
         *
         * @param userId the user ID
         * @param email the email address
         */
        public User(String userId, String email) {
            this.userId = userId;
            this.email = email;
        }

        /**
         * Gets the user ID.
         *
         * @return the user ID
         */
        public String getUserId() {
            return userId;
        }

        /**
         * Sets the user ID.
         *
         * @param userId the user ID to set
         */
        public void setUserId(String userId) {
            this.userId = userId;
        }

        /**
         * Gets the email address.
         *
         * @return the email address
         */
        public String getEmail() {
            return email;
        }

        /**
         * Sets the email address.
         *
         * @param email the email address to set
         */
        public void setEmail(String email) {
            this.email = email;
        }
        
        // Add any additional methods or properties here
    }
    
    public User getUserFromPubsubMessage(Map<String, Object> map) {

        String userId = String.valueOf(Optional.ofNullable(map.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'userId' in data")));

        String email = Optional.ofNullable((String) map.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("Missing 'email' in data"));

        return new User(userId, email);
    }

}

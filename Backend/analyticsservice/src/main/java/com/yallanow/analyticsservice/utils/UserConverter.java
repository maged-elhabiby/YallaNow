package com.yallanow.analyticsservice.utils;

import com.yallanow.analyticsservice.models.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserConverter {

    public Map<String, Object> convertUserToRecombeeMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());

        return map;
    }

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

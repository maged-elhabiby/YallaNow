package com.yallanow.analyticsservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yallanow.analyticsservice.models.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserConverter implements Converter<User> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> toRecombeeMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getUserId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("age", user.getAge());
        map.put("gender", user.getGender());
        map.put("interests", user.getInterests());
        return map;
    }

    @Override
    public User fromPubsubMessage(String message) throws IOException {
        Map<String, Object> payload = objectMapper.readValue(message, Map.class);
        Map<String, Object> itemData = (Map<String, Object>) payload.get("item");
        return fromMap(itemData);
    }

    @Override
    public User fromMap(Map<String, Object> map) {
        String userId = (String) map.get("userId");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        Integer age = (Integer) map.get("age");
        String gender = (String) map.get("gender");
        List<String> interests = (List<String>) map.get("interests");

        return new User(userId, name, email, age, gender, interests);
    }
}

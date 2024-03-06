package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.models.User;

public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUser(String userId);
}

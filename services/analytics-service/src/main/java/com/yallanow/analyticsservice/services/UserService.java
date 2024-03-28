package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.exceptions.UserServiceException;
import com.yallanow.analyticsservice.models.User;

public interface UserService {
    void addUser(User user) throws UserServiceException;

    void updateUser(User user) throws UserServiceException;

    void deleteUser(String userId) throws UserServiceException;
}

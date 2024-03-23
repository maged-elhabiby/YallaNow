package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.UserServiceException;
import com.yallanow.analyticsservice.models.User;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final RecombeeClientInterface recombeeClient;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(RecombeeClientInterface recombeeClient, UserConverter userConverter) {
        this.recombeeClient = recombeeClient;
        this.userConverter = userConverter;
    }

    @Override
    public void addUser(User user) throws UserServiceException {
        try {
            recombeeClient.addUser(user.getUserId(), userConverter.convertUserToRecombeeMap(user));
        }  catch (Exception e) {
            throw new UserServiceException("Error adding user to Recombee", e);
        }
    }

    @Override
    public void updateUser(User user) throws UserServiceException {
        try {
            recombeeClient.updateUser(user.getUserId(), userConverter.convertUserToRecombeeMap(user));
        } catch (Exception e) {
            throw new UserServiceException("Error updating user in Recombee", e);
        }
    }

    // Change to soft deletes
    @Override
    public void deleteUser(String userId) throws UserServiceException {
        try {
            recombeeClient.deleteUser(userId);
        } catch (Exception e) {
            throw new UserServiceException("Error deleting user from Recombee", e);
        }
    }

}

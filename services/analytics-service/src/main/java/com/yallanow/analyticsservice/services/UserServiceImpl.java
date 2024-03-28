package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.UserServiceException;
import com.yallanow.analyticsservice.models.User;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final RecombeeClientInterface recombeeClient;
    private final UserConverter userConverter;

    /**
     * This class implements the UserService interface and provides the implementation
     * for managing user-related operations.
     */
    @Autowired
    public UserServiceImpl(RecombeeClientInterface recombeeClient, UserConverter userConverter) {
        this.recombeeClient = recombeeClient;
        this.userConverter = userConverter;
    }

    /**
     * Adds a user to the system.
     *
     * @param user the user to be added
     * @throws UserServiceException if there is an error adding the user
     */
    @Override
    public void addUser(User user) throws UserServiceException {
        try {
            Map<String, Object> userMap = userConverter.convertUserToRecombeeMap(user);
            recombeeClient.addUser(user.getUserId(), userMap);
        } catch (Exception e) {
            throw new UserServiceException("Error adding user to Recombee: " + e.getMessage(), e);
        }
    }

    /**
        * Updates a user in the Recombee service.
        *
        * @param user the user object containing the updated information
        * @throws UserServiceException if there is an error updating the user in Recombee
        */
    @Override
    public void updateUser(User user) throws UserServiceException {
        try {
            recombeeClient.updateUser(user.getUserId(), userConverter.convertUserToRecombeeMap(user));
        } catch (Exception e) {
            throw new UserServiceException("Error updating user in Recombee: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deletes a user from the analytics service.
     *
     * @param userId the ID of the user to be deleted
     * @throws UserServiceException if there is an error deleting the user
     */
    @Override
    public void deleteUser(String userId) throws UserServiceException {
        try {
            recombeeClient.deleteUser(userId);
        } catch (Exception e) {
            throw new UserServiceException("Error deleting user from Recombee: " + e.getMessage(), e);
        }
    }

}

package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.ValidationException;
import com.yallanow.analyticsservice.models.User;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yallanow.analyticsservice.services.ExceptionHelper.handleRecombeeClientException;
import static com.yallanow.analyticsservice.services.ExceptionHelper.handleValidationException;

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
    public void addUser(User user) {
        try {
            recombeeClient.addUser(user.getUserId(), userConverter.toRecombeeMap(user));
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error adding item to Recombee", e);
        } catch (ValidationException e) {
            handleValidationException("Item has invalid properties", e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            recombeeClient.updateUser(user.getUserId(), userConverter.toRecombeeMap(user));
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error adding item to Recombee", e);
        } catch (ValidationException e) {
            handleValidationException("Item has invalid properties", e);
        }
    }

    // Change to soft deletes
    @Override
    public void deleteUser(User user) {
        try {
            recombeeClient.deleteUser(user.getUserId());
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error adding item to Recombee", e);
        }
    }

}

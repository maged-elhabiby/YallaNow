package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.UserServiceException;
import com.yallanow.analyticsservice.models.User;
import com.yallanow.analyticsservice.utils.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private RecombeeClientInterface recombeeClient;

    @Mock
    private UserConverter userConverter;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(recombeeClient, userConverter);
    }

    @Test
    void addUser_Success() throws UserServiceException, RecombeeClientException {
        User user = new User("userId", "email");

        Map<String, Object> recombeeMap = new HashMap<>();
        when(userConverter.convertUserToRecombeeMap(user)).thenReturn(recombeeMap);

        userService.addUser(user);

        verify(recombeeClient, times(1)).addUser("userId", recombeeMap);
    }

    @Test
    void addUser_Exception() {
        User user = new User("userId", "email");

        when(userConverter.convertUserToRecombeeMap(user)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(UserServiceException.class, () -> userService.addUser(user));
    }


    @Test
    void deleteUser_Success() throws UserServiceException, RecombeeClientException {
        String userId = "userId";

        userService.deleteUser(userId);

        verify(recombeeClient, times(1)).deleteUser("userId");
    }

    @Test
    void deleteUser_Exception() throws RecombeeClientException {
        String userId = "userId";

        doThrow(new RuntimeException("Test Exception")).when(recombeeClient).deleteUser("userId");

        assertThrows(UserServiceException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void updateUser_Success() throws UserServiceException, RecombeeClientException {
        User user = new User("userId", "email");

        Map<String, Object> recombeeMap = new HashMap<>();
        when(userConverter.convertUserToRecombeeMap(user)).thenReturn(recombeeMap);

        userService.updateUser(user);

        verify(recombeeClient, times(1)).updateUser("userId", recombeeMap);

    }

    @Test
    void updateUser_Exception() {
        User user = new User("userId", "email");

        when(userConverter.convertUserToRecombeeMap(user)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(UserServiceException.class, () -> userService.updateUser(user));
    }

}


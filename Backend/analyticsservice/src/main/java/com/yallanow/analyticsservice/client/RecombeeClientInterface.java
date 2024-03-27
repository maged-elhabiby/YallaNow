package com.yallanow.analyticsservice.client;

import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.ValidationException;

import java.util.Map;

public interface RecombeeClientInterface {

    // Item
    void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void deleteItem(String itemId) throws RecombeeClientException;
    Map<String, Object> getItem(String itemId) throws RecombeeClientException;

    // User
    void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException;
    void updateUser(String userId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void deleteUser(String userId) throws RecombeeClientException;
    Map<String, Object> getUser(String userId) throws RecombeeClientException;

    void setItemProperties() throws RecombeeClientException;

    void setUserProperties() throws RecombeeClientException;

}

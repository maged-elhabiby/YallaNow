package com.yallanow.analyticsservice.client;

import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.ValidationException;

import java.util.Map;

public interface RecombeeClientInterface {

    // Item
    void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void deleteItem(String itemId) throws RecombeeClientException;
    Map<String, Object> getItem(String itemId) throws RecombeeClientException;

    // User
    void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException, ValidationException;
    void updateUser(String userId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void deleteUser(String userId) throws RecombeeClientException;
    Map<String, Object> getUser(String userId) throws RecombeeClientException;

    // Interactions
    void addDetailView(String userId, String itemId, String recommId)  throws RecombeeClientException;
    void deleteDetailView(String userId, String itemId)  throws RecombeeClientException;
    void addPurchase(String userId, String itemId)  throws RecombeeClientException;
    void deletePurchase(String userId, String itemId)  throws RecombeeClientException;

}

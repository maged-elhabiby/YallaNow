package com.yallanow.analyticsservice.client;

import com.yallanow.analyticsservice.exceptions.RecombeeClientException;

import java.util.Map;

/**
 * This interface represents the client interface for interacting with the Recombee service.
 * It provides methods for managing items and users in the Recombee database.
 */
public interface RecombeeClientInterface {

    /**
     * Adds an item to the Recombee database with the specified item ID and properties.
     *
     * @param itemId          the ID of the item to be added
     * @param itemProperties  the properties of the item to be added
     * @throws RecombeeClientException if there is an error adding the item
     */
    void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;

    /**
     * Updates an existing item in the Recombee database with the specified item ID and properties.
     *
     * @param itemId          the ID of the item to be updated
     * @param itemProperties  the updated properties of the item
     * @throws RecombeeClientException if there is an error updating the item
     */
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;

    /**
     * Deletes an item from the Recombee database with the specified item ID.
     *
     * @param itemId  the ID of the item to be deleted
     * @throws RecombeeClientException if there is an error deleting the item
     */
    void deleteItem(String itemId) throws RecombeeClientException;

    /**
     * Adds a user to the Recombee database with the specified user ID and properties.
     *
     * @param userId          the ID of the user to be added
     * @param userProperties  the properties of the user to be added
     * @throws RecombeeClientException if there is an error adding the user
     */
    void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException;

    /**
     * Updates an existing user in the Recombee database with the specified user ID and properties.
     *
     * @param userId          the ID of the user to be updated
     * @param userProperties  the updated properties of the user
     * @throws RecombeeClientException if there is an error updating the user
     */
    void updateUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException;

    /**
     * Deletes a user from the Recombee database with the specified user ID.
     *
     * @param userId  the ID of the user to be deleted
     * @throws RecombeeClientException if there is an error deleting the user
     */
    void deleteUser(String userId) throws RecombeeClientException;

    /**
     * Sets the properties of an item in the Recombee database.
     * This method is used to define the properties of items that will be added in the future.
     *
     * @throws RecombeeClientException if there is an error setting the item properties
     */
    void setItemProperties() throws RecombeeClientException;

    /**
     * Sets the properties of a user in the Recombee database.
     * This method is used to define the properties of users that will be added in the future.
     *
     * @throws RecombeeClientException if there is an error setting the user properties
     */
    void setUserProperties() throws RecombeeClientException;

}

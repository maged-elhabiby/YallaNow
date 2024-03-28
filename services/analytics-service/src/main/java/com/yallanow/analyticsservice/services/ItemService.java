package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.models.Item;

/**
 * The ItemService interface provides methods for managing items.
 */
public interface ItemService {

    /**
     * Adds a new item.
     *
     * @param item the item to be added
     * @throws ItemServiceException if an error occurs while adding the item
     */
    void addItem(Item item) throws ItemServiceException;

    /**
     * Updates an existing item.
     *
     * @param item the item to be updated
     * @throws ItemServiceException if an error occurs while updating the item
     */
    void updateItem(Item item) throws ItemServiceException;

    /**
     * Deletes an item by its ID.
     *
     * @param itemId the ID of the item to be deleted
     * @throws ItemServiceException if an error occurs while deleting the item
     */
    void deleteItem(String itemId) throws ItemServiceException;
}

package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.utils.ItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements the ItemService interface and provides methods to add, update, and delete items using the RecombeeClientInterface.
 * It uses the ItemConverter to convert Item objects to RecombeeMap objects.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final RecombeeClientInterface recombeeClient;
    private final ItemConverter itemConverter;

    /**
     * This class represents the implementation of the ItemService interface.
     * It provides methods to interact with the recombeeClient and itemConverter.
     */
    @Autowired
    public ItemServiceImpl(RecombeeClientInterface recombeeClient, ItemConverter itemConverter) {
        this.recombeeClient = recombeeClient;
        this.itemConverter = itemConverter;
    }

    /**
     * Adds an item to the analytics service.
     *
     * @param item the item to be added
     * @throws ItemServiceException if there is an error adding the item to Recombee
     */
    @Override
    public void addItem(Item item) throws ItemServiceException {
        try {
            recombeeClient.addItem(item.getItemId(), itemConverter.convertItemToRecombeeMap(item));
        } catch (Exception e) {
            throw new ItemServiceException("Error adding item to Recombee: " + e.getMessage(), e);
        }
    }

    /**
        * Updates an item in the Recombee service.
        *
        * @param item The item to be updated.
        * @throws ItemServiceException If an error occurs while updating the item in Recombee.
        */
    @Override
    public void updateItem(Item item) throws ItemServiceException {
        try {
            recombeeClient.updateItem(item.getItemId(), itemConverter.convertItemToRecombeeMap(item));
        } catch (Exception e) {
            throw new ItemServiceException("Error updating item in Recombee: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an item from the analytics service.
     *
     * @param itemId the ID of the item to be deleted
     * @throws ItemServiceException if there is an error deleting the item
     */
    @Override
    public void deleteItem(String itemId) throws ItemServiceException {
        try {
            recombeeClient.deleteItem(itemId);
        } catch (Exception e) {
            throw new ItemServiceException("Error deleting item from Recombee: " + e.getMessage(), e);
        }
    }

}

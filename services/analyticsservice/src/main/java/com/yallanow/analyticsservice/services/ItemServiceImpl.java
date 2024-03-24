package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.utils.ItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final RecombeeClientInterface recombeeClient;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(RecombeeClientInterface recombeeClient, ItemConverter itemConverter) {
        this.recombeeClient = recombeeClient;
        this.itemConverter = itemConverter;
    }

    @Override
    public void addItem(Item item) throws ItemServiceException {
        try {
            recombeeClient.addItem(item.getItemId(), itemConverter.convertItemToRecombeeMap(item));
        } catch (Exception e) {
            throw new ItemServiceException("Error adding item to Recombee: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateItem(Item item) throws ItemServiceException {
        try {
            recombeeClient.updateItem(item.getItemId(), itemConverter.convertItemToRecombeeMap(item));
        } catch (Exception e) {
            throw new ItemServiceException("Error updating item in Recombee: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteItem(String itemId) throws ItemServiceException {
        try {
            recombeeClient.deleteItem(itemId);
        } catch (Exception e) {
            throw new ItemServiceException("Error deleting item from Recombee: " + e.getMessage(), e);
        }
    }

}

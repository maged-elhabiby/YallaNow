package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.ValidationException;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.utils.ItemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.yallanow.analyticsservice.services.ExceptionHelper.handleRecombeeClientException;
import static com.yallanow.analyticsservice.services.ExceptionHelper.handleValidationException;

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
    public void addItem(Item item) {
        try {
            recombeeClient.addItem(item.getItemId(), itemConverter.toRecombeeMap(item));
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error adding item to Recombee", e);
        } catch (ValidationException e) {
            handleValidationException("Item has invalid properties", e);
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            recombeeClient.updateItem(item.getItemId(), itemConverter.toRecombeeMap(item));
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error updating item in Recombee", e);
        } catch (ValidationException e) {
            handleValidationException("Item has invalid properties", e);
        }
    }

    // Change to soft deletes
    @Override
    public void deleteItem(Item item) {
        try {
            recombeeClient.deleteItem(item.getItemId());
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error deleting item from Recombee", e);
        }
    }

    @Override
    public Item getItem(String itemId) {
        try {
            return itemConverter.fromMap(recombeeClient.getItem(itemId));
        } catch (RecombeeClientException e) {
            handleRecombeeClientException("Error getting item from Recombee", e);
        }
        return null;
    }


}

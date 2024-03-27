package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.models.Item;

public interface ItemService {

    void addItem(Item item) throws ItemServiceException;
    void updateItem(Item item) throws ItemServiceException;
    void deleteItem(String itemId) throws ItemServiceException;
}

package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.models.Item;

public interface ItemService {

    void addItem(Item item);
    void updateItem(Item item);
    void deleteItem(Item item);
    Item getItem(String itemId);
}

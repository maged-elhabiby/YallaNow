package com.yallanow.analyticsservice.services;

public interface InteractionsService {

    // Detail view interactions
    void addDetailView(String userId, String itemId, String recommId);
    void deleteDetailView(String userId, String itemId);

    // Purchase interactions
    void addPurchase(String userId, String itemId);
    void deletePurchase(String userId, String itemId);

}

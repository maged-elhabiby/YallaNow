package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionsServiceImpl implements InteractionsService {

    private final RecombeeClientInterface recombeeClient;

    @Autowired
    public InteractionsServiceImpl(RecombeeClientInterface recombeeClient) {
        this.recombeeClient = recombeeClient;
    }

    @Override
    public void addDetailView(String userId, String itemId, String recommId) {
        try {
            recombeeClient.addDetailView(userId, itemId, recommId);
        } catch (RecombeeClientException e) {
            ExceptionHelper.handleRecombeeClientException("Error adding detail view", e);
        }
    }

    @Override
    public void deleteDetailView(String userId, String itemId) {
        try {
            recombeeClient.deleteDetailView(userId, itemId);
        } catch (RecombeeClientException e) {
            ExceptionHelper.handleRecombeeClientException("Error adding detail view", e);
        }
    }


    @Override
    public void addPurchase(String userId, String itemId) {
        try {
            recombeeClient.addPurchase(userId, itemId);
        } catch (RecombeeClientException e) {
            ExceptionHelper.handleRecombeeClientException("Error adding purchase", e);
        }
    }

    @Override
    public void deletePurchase(String userId, String itemId) {
        try {
            recombeeClient.deletePurchase(userId, itemId);
        } catch (RecombeeClientException e) {
            ExceptionHelper.handleRecombeeClientException("Error deleting purchase", e);
        }
    }
}

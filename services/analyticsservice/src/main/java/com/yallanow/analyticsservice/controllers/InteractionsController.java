package com.yallanow.analyticsservice.controllers;

import com.yallanow.analyticsservice.services.InteractionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interactions")
public class InteractionsController {

    private final InteractionsService interactionsService;

    @Autowired
    public InteractionsController(InteractionsService interactionsService) {
        this.interactionsService = interactionsService;
    }

    // Endpoint for adding a detail view interaction
    @PostMapping("/detail-view/add")
    public ResponseEntity<?> addDetailView(@RequestParam String userId,
                                           @RequestParam String itemId,
                                           @RequestParam(required = false) String recommId) {
        interactionsService.addDetailView(userId, itemId, recommId);
        return ResponseEntity.ok().build();
    }

    // Endpoint for deleting a detail view interaction
    @DeleteMapping("/detail-view/delete")
    public ResponseEntity<?> deleteDetailView(@RequestParam String userId, @RequestParam String itemId) {
        interactionsService.deleteDetailView(userId, itemId);
        return ResponseEntity.ok().build();
    }

    // Endpoint for adding a purchase interaction
    @PostMapping("/purchase/add")
    public ResponseEntity<?> addPurchase(@RequestParam String userId, @RequestParam String itemId) {
        interactionsService.addPurchase(userId, itemId);
        return ResponseEntity.ok().build();
    }

    // Endpoint for deleting a purchase interaction
    @DeleteMapping("/purchase/delete")
    public ResponseEntity<?> deletePurchase(@RequestParam String userId, @RequestParam String itemId) {
        interactionsService.deletePurchase(userId, itemId);
        return ResponseEntity.ok().build();
    }
}

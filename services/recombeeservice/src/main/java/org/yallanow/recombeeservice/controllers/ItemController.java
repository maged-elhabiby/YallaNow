package org.yallanow.recombeeservice.controllers;

import org.yallanow.recombeeservice.services.ItemServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {

    private final ItemService itemService;

    @Autowired
    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Add a new item to Recombee
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    // Remove an item from Recombee
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {

        return ResponseEntity.noContent().build();
    }

    // Retrieve a list of items from Recombee
    @GetMapping
    public ResponseEntity<List<Item>> listItems() {

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Set properties for an item in Recombee
    @PostMapping("/{itemId}/values")
    public ResponseEntity<Void> setItemValues(@PathVariable String itemId, @RequestBody Map<String, Object> values) {

        return ResponseEntity.ok().build();
    }

    // Retrieve property values of an item from Recombee
    @GetMapping("/{itemId}/values")
    public ResponseEntity<Map<String, Object>> getItemValues(@PathVariable String itemId) {
        return ResponseEntity.ok(itemValues);
    }
}

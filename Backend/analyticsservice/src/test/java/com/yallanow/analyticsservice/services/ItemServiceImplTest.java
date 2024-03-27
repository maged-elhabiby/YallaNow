package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.client.RecombeeClientInterface;
import com.yallanow.analyticsservice.exceptions.ItemServiceException;
import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.models.Item;
import com.yallanow.analyticsservice.utils.ItemConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

class ItemServiceImplTest {

    @Mock
    private RecombeeClientInterface recombeeClient;

    @Mock
    private ItemConverter itemConverter;

    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        itemService = new ItemServiceImpl(recombeeClient, itemConverter);
    }

    @Test
    void addItem_Success() throws ItemServiceException, RecombeeClientException {
        Item item = new Item("itemId", "groupId", "Title", "Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), null, 0, 10, "Active", "image-url");

        Map<String, Object> recombeeMap = new HashMap<>();
        when(itemConverter.convertItemToRecombeeMap(item)).thenReturn(recombeeMap);

        itemService.addItem(item);

        verify(recombeeClient, times(1)).addItem("itemId", recombeeMap);
    }

    @Test
    void addItem_Exception() {
        Item item = new Item("itemId", "groupId", "Title", "Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), null, 0, 10, "Active", "image-url");

        when(itemConverter.convertItemToRecombeeMap(item)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(ItemServiceException.class, () -> itemService.addItem(item));
    }

    @Test
    void updateItem_Success() throws ItemServiceException, RecombeeClientException {
        Item item = new Item("itemId", "groupId", "Title", "Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), null, 0, 10, "Active", "image-url");

        Map<String, Object> recombeeMap = new HashMap<>();
        when(itemConverter.convertItemToRecombeeMap(item)).thenReturn(recombeeMap);

        itemService.updateItem(item);

        verify(recombeeClient, times(1)).updateItem("itemId", recombeeMap);
    }


    @Test
    void updateItem_Exception() {
        Item item = new Item("itemId", "groupId", "Title", "Description", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), null, 0, 10, "Active", "image-url");

        when(itemConverter.convertItemToRecombeeMap(item)).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(ItemServiceException.class, () -> itemService.updateItem(item));
    }

    @Test
    void deleteItem_Success() throws ItemServiceException, RecombeeClientException {
        itemService.deleteItem("itemId");

        verify(recombeeClient, times(1)).deleteItem("itemId");
    }

    @Test
    void deleteItem_Exception() throws RecombeeClientException {
        doThrow(new RuntimeException("Test Exception")).when(recombeeClient).deleteItem("itemId");

        assertThrows(ItemServiceException.class, () -> itemService.deleteItem("itemId"));
    }

}

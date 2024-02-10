package com.yallanow.recombeeservice.client;

import com.yallanow.recombeeservice.exceptions.RecombeeClientException;
import java.util.Map;

public interface RecombeeClient {

    void addItem(Map<String, Object> itemProperties) throws RecombeeClientException;
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void deleteItem(String itemId) throws RecombeeClientException;
    Map<String, Object> getItem(String itemId) throws RecombeeClientException;

}

package org.yallanow.recombeeservice.client;

import org.yallanow.recombeeservice.exceptions.RecombeeClientException;
import java.util.Map;

public interface RecombeeClientInterface {

    void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException;
    void deleteItem(String itemId) throws RecombeeClientException;
    Map<String, Object> getItem(String itemId) throws RecombeeClientException;

}

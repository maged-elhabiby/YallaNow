package org.yallanow.recombeeservice.client;

import io.micrometer.core.instrument.config.validate.Validated;
import org.yallanow.recombeeservice.exceptions.RecombeeClientException;
import org.yallanow.recombeeservice.exceptions.ValidationException;

import java.util.Map;

public interface RecombeeClientInterface {

    // Item
    void addItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void updateItem(String itemId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void deleteItem(String itemId) throws RecombeeClientException;
    Map<String, Object> getItem(String itemId) throws RecombeeClientException;

    // User
    void addUser(String userId, Map<String, Object> userProperties) throws RecombeeClientException, ValidationException;
    void updateUser(String userId, Map<String, Object> itemProperties) throws RecombeeClientException, ValidationException;
    void deleteUser(String userId) throws RecombeeClientException;
    Map<String, Object> getUser(String userId) throws RecombeeClientException;

    // User Item Interactions

    // User opens an event.
    void addDetailView(String userId, String itemId, String recommId)  throws RecombeeClientException;
    void deleteDetailView(String userId, String itemId)  throws RecombeeClientException;

    // User RSVP's an event.
    void addPurchase(String userId, String itemId)  throws RecombeeClientException;
    void deletePurchase(String userId, String itemId)  throws RecombeeClientException;

    // void addRating();
    // void deleteRating();

    // void addCartAddition();
    // void deleteCartAddition();

    // void addBookmark();
    // void deleteBookmark();


    // Recommendations
    void recommend(RecommendationRequest request) throws RecombeeClientException;

    // void recommendNextItems(String recommId, long count);
    // void recommendItemsToItem(String itemId, String targetUserId, long count,
                              String scenario, boolean returnProperties, String filter);
    // void recommendItemsToUser(String userId, long count, String scenario,
                              boolean returnProperties, String filter);

    // void recommendItemSegmentsToUser();
    // void recommendItemSegmentsToItem();
    // void recommendItemSegmentsToSegments();

    // void recommendUsersToUsers();
    // void recommendUsersToItem();

    // Search
    void searchItems(String userId, String searchQuery, long count, String scenario,
                     boolean returnProperties, String filter);
    // void searchItemSegments();

}

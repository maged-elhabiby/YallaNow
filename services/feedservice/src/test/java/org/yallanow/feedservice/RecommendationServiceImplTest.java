package org.yallanow.feedservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.yallanow.feedservice.client.RecombeeClientInterface;
import org.yallanow.feedservice.exceptions.RecombeeClientException;
import org.yallanow.feedservice.exceptions.RecommendationException;
import org.yallanow.feedservice.models.RecommendationRequest;
import org.yallanow.feedservice.models.RecommendationResponse;

import org.yallanow.feedservice.services.RecommendationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.yallanow.feedservice.models.RecommendationType.ItemsToUser;
import static org.yallanow.feedservice.models.RecommendationType.SearchItems;

@SpringBootTest
class RecommendationServiceImplTest {

    @Mock
    RecombeeClientInterface recombeeClient;

    @InjectMocks
    RecommendationServiceImpl recommendationService;

    @Test
    void searchItems_InvalidRequest_ThrowsRecommendationException() {
        // Arrange
        RecommendationRequest request = new RecommendationRequest();
        // Missing required fields in request

        // Act & Assert
        assertThrows(RecommendationException.class, () -> recommendationService.searchItems(request));
    }

    @Test
    void searchItems_RecombeeClientException_ThrowsRecommendationException() throws RecombeeClientException {
        // Arrange
        RecommendationRequest request = new RecommendationRequest();
        request.setRecommendationType(SearchItems);
        request.setUserId("user123");
        request.setSearchQuery("test");
        request.setCount(5);
        when(recombeeClient.searchItems(anyString(), anyString(), anyInt(), anyString()))
                .thenThrow(new RecombeeClientException("Error"));

        // Act & Assert
        assertThrows(RecommendationException.class, () -> recommendationService.searchItems(request));
    }

    // Add more test cases for other methods in RecommendationServiceImpl
}
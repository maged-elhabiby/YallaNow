import axios from "axios";
const baseUrl = 'http://localhost:8080/recommendations';

const feedService = {

    formatRecommendationsToEvents: async (recommendations) => {
        return recommendations.map((recommendation) => {
          const formattedEvent = {
            ...recommendation.properties,
            eventId: recommendation.id,
            eventStartTime: new Date(recommendation.properties.eventStartTime * 1000),
            eventEndTime: new Date(recommendation.properties.eventEndTime * 1000)
          };
      
          return formattedEvent;
        });
      },

    getRecommendations: async (recommendationRequest) => {
        try {
            const response = await axios.post(baseUrl, recommendationRequest, {
                headers: {
                    'Content-Type': 'application/json',
                    'mode': 'cors'
                },
            });
            // Format the reponse data
            const data = response.data;
            data.recommendations = feedService.formatRecommendationsToEvents(data.recommendations);
            return data;

        } catch (error) {
            console.error('Error fetching recommendations:', error);
            throw error;
        }
    },

    //getDefault -- universal model
    getDefaultEvents: async (userId, count) => {
        const request = {
            userId: userId,
            count: count,
            scenario: 'default-items',
            recommendationType: 'ItemsToUser'
        };
        return await feedService.getRecommendations(request);
    },

    //getHomepageEvents -- homepage or welcome screen scenarios
    getHomepageEvents: async (userId, count) => {
        const request = {
            userId: userId,
            count: count,
            scenario: 'homepage-items',
            recommendationType: 'ItemsToUser'
        };
        return await feedService.getRecommendations(request);
    },

    //getPersonalEvents -- personalized events
    getPersonalEvents: async (userId, count) => {
        const request = {
            userId: userId,
            count: count,
            scenario: 'personal-items',
            recommendationType: 'ItemsToUser'
        };
        return await feedService.getRecommendations(request);
    },

    //getPopularEvents -- globally recommended events
    getPopularEvents: async (userId, count) => {
        const request = {
            userId: userId,
            count: count,
            scenario: 'popular-items',
            recommendationType: 'ItemsToUser'
        };
        return await feedService.getRecommendations(request);
    },

    //getRecentlyViewedEvents -- recently viewed events
    getRecentylViewedEvents: async (userId, count) => {
        const request = {
            userId: userId,
            count: count,
            scenario: 'recently-viewed-items',
            recommendationType: 'ItemsToUser'
        };
        return await feedService.getRecommendations(request);
    },

    //getNextEvents -- gets the next items for a event feed
    //  pass in the recommId from one of the getEvents feeds
    //  to get additional recommendations. Used for infinate feeds.
    getNextEvents: async (count, recommId) => {
        const request = {
            count: count,
            recommId: recommId,
            recommendationType: 'NextItems'
        };
        return await feedService.getRecommendations(request);
    },

    //searchEvents -- personalized full text search of events
    searchEvents: async (userId, count, searchQuery) => {
        const request = {
            userId: userId,
            count: count,
            searchQuery: searchQuery,
            scenario: 'search-items-personalized',
            recommendationType: 'SearchItems'
        };
        return await feedService.getRecommendations(request);
    }
};




export default feedService;

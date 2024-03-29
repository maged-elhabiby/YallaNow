import axios from "axios";
import config from "../config/config";

class FeedService {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    formatRecommendationsToEvents(recommendations) {
        return recommendations.map((recommendation) => {
            return {
                ...recommendation.properties,
                eventId: recommendation.id,
                eventStartTime: new Date(recommendation.properties.eventStartTime * 1000),
                eventEndTime: new Date(recommendation.properties.eventEndTime * 1000)
            };
        });
    }

    async getRecommendations(recommendationRequest) {
        try {
            const response = await axios.post(this.baseUrl, recommendationRequest, {
                headers: {
                    'Content-Type': 'application/json',
                    'mode': 'cors'
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    async getDefaultEvents(userId, count) {
        const request = {
            userId: userId,
            count: count,
            scenario: 'default-items',
            recommendationType: 'ItemsToUser'
        };
        return await this.getRecommendations(request);
    }

    async getHomepageEvents(userId, count) {
        const request = {
            userId: userId,
            count: count,
            scenario: 'homepage-items',
            recommendationType: 'ItemsToUser'
        };
        return await this.getRecommendations(request);
    }

    async getPersonalEvents(userId, count) {
        const request = {
            userId: userId,
            count: count,
            scenario: 'personal-items',
            recommendationType: 'ItemsToUser'
        };
        return await this.getRecommendations(request);
    }

    async getPopularEvents(userId, count) {
        const request = {
            userId: userId,
            count: count,
            scenario: 'popular-items',
            recommendationType: 'ItemsToUser'
        };
        return await this.getRecommendations(request);
    }

    async getRecentylViewedEvents(userId, count) {
        const request = {
            userId: userId,
            count: count,
            scenario: 'recently-viewed-items',
            recommendationType: 'ItemsToUser'
        };
        return await this.getRecommendations(request);
    }

    async getNextEvents(count, recommId) {
        const request = {
            count: count,
            recommId: recommId,
            recommendationType: 'NextItems'
        };
        return await this.getRecommendations(request);
    }

    async searchEvents(userId, count, searchQuery) {
        const request = {
            userId: userId,
            count: count,
            searchQuery: searchQuery,
            scenario: 'search-items-personalized',
            recommendationType: 'SearchItems'
        };
        return await this.getRecommendations(request);
    }

    handleResponse(response) {
        switch (response.status) {
            case 200:
                return {
                    recommId: response.data.recommId,
                    recommendations: this.formatRecommendationsToEvents(response.data.recommendations)
                };
            case 400:
                throw new Error("Bad request: " + response.data.message);
            case 403:
                throw new Error("Access denied.");
            case 404:
                throw new Error("Resource not found.");
            case 422:
                throw new Error("Unprocessable entity: " + response.data.message);
            default:
                throw new Error("Error processing request.");
        }
    }
}

const feedService = new FeedService(config.feedBaseUrl);
export default feedService;

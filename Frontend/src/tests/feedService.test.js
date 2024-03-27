const feedService = require('../api/feedService');

describe('feedService', () => {
    const userId = '10251';
    const count = 10;

    it('should get default events', async () => {
        const response = await feedService.getDefaultEvents(userId, count);
        expect(response).toBeDefined();
        console.log("getDefaultEvents payload:")
        console.log('Events response:', response);
        console.log('First event from recommendations:', response.recommendations[0])
    });

    it('should get homepage events', async () => {
        const response = await feedService.getHomepageEvents(userId, count);
        expect(response).toBeDefined();
    });

    it('should get personal events', async () => {
        const response = await feedService.getPersonalEvents(userId, count);
        expect(response).toBeDefined();
    });

    it('should get popular events', async () => {
        const response = await feedService.getPopularEvents(userId, count);
        expect(response).toBeDefined();
    });

    it('should get recently viewed events', async () => {
        const response = await feedService.getRecentylViewedEvents(userId, count);
        expect(response).toBeDefined();
    });

    it('should get next events after personal events', async () => {
        const userId = 'test-user';
        const count = 10;
        
        const personalEventsResponse = await feedService.getPersonalEvents(userId, count);
        expect(personalEventsResponse).toBeDefined();
        const recommId = personalEventsResponse.recommId;

        const nextEventsResponse = await feedService.getNextEvents(count, recommId);
        expect(nextEventsResponse).toBeDefined();
    });

    it('should search events', async () => {
        const searchQuery = 'concerts';
        const response = await feedService.searchEvents(userId, count, searchQuery);
        expect(response).toBeDefined();
    });
    
});

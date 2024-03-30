import recommData from './recommendations.json';

const formatRecommendationsToEvents = (recommendations) => {
    return recommendations.map((recommendation) => {
        const formattedEvent = {
            ...recommendation.properties,
            eventId: recommendation.id,
            eventStartTime: new Date(recommendation.properties.eventStartTime * 1000),
            eventEndTime: new Date(recommendation.properties.eventEndTime * 1000)
        };

        return formattedEvent;
    });
};

const getMockRecommendations = () => {
    const data = {
        recommId: recommData.recommId,
        recommendations: formatRecommendationsToEvents(recommData.recommendations)
    }
    console.log(data);
    return data;
};

export default getMockRecommendations;


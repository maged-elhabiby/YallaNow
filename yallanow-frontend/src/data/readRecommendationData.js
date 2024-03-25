import recommendationsData from './recommendations.json';

const formatRecommendationsToEvents = (recommendations) => {
  return recommendations.map((recommendation) => {
    return {
      ...recommendation.properties,
      eventId: recommendation.id
    };
  });
};

const getEvents = () => {
  return formatRecommendationsToEvents(recommendationsData.recommendations);
};

export default getEvents;

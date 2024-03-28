import React, { useEffect, useState } from 'react';
import EventCard from '../components/EventCard';
import feedService from '../api/feedService';
import {  auth } from '../firebase-config';

const ExplorePage = () => {
  console.log("Rendering ExplorePage");

  const userId = 10000;
  const initialiEventsCount = 20
  const [events, setEvents] = useState([]);
  const [homepageRecommId, setHomepageRecommId] = useState(null);
  const [loading, setLoading] = useState(false);


  useEffect(() => {
    console.log("Current user: ", auth.currentUser);
  }, []);
  const fetchHomepageEvents = async () => {
    setLoading(true);
    try {

      const data = await feedService.getHomepageEvents(userId, initialiEventsCount);
      setEvents(data.recommendations ?? []);
      setHomepageRecommId(data.recommId);
    } catch (error) {
      console.error('Error fetching homepage events:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchNextEvents = async () => {
    if (!homepageRecommId || loading) return;
    setLoading(true);
    try {
      console.log('Loading more events...');
      const count = 20; // Number of additional events to fetch
      const data = await feedService.getNextEvents(count, homepageRecommId);
      setEvents((prevEvents) => [...prevEvents, ...data.recommendations]);
      setHomepageRecommId(data.recommId);
    } catch (error) {
      console.error('Error fetching next events:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchHomepageEvents();
  }, []);


  return (
    <div className="mt-20">

      <div className="container mx-auto py-8 px-4">
        <h2 className="text-4xl font-semibold mb-6">Explore Events</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {events.map((event) => (
            <EventCard key={event.eventId} event={event} recommId={homepageRecommId} />
          ))}
        </div>

        <div className="flex justify-center mt-10">
        {loading && <p>Loading more events...</p>}
        {!loading && homepageRecommId && (

          <button
            type="button"
            className="rounded-md bg-pink-600 px-10 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-pink-500 focus-visible:outline focus-visible:outline-2 
            focus-visible:outline-offset-2 focus-visible:outline-pink-600"
            onClick={fetchNextEvents}
          >
            Load More Events
          </button>
          )}
        </div>
      </div>
    </div>
  );
}

export default ExplorePage;

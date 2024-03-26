import React, { useEffect, useState } from 'react';
import EventCard from '../components/EventCard';
import feedService from '../api/feedService';

const ExplorePage = () => {
  const [events, setEvents] = useState([]);
  const [recommId, setRecommId] = useState(null);
  const [loading, setLoading] = useState(false);

    useEffect(() => {
    const isSignedIn = localStorage.getItem('SignedIn');
    if (isSignedIn === "false") {
      window.location.href = '/signin';
    }
  }, []);



  const fetchHomepageEvents = async () => {
    setLoading(true);
    try {
      const userId = 'your-user-id'; // Replace with actual user ID
      const count = 20; // Number of events to fetch initially
      const data = await feedService.getHomepageEvents(userId, count);
      setEvents(data.recommendations ?? []);
      setRecommId(data.recommId);
    } catch (error) {
      console.error('Error fetching homepage events:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchNextEvents = async () => {
    if (!recommId || loading) return;
    setLoading(true);
    try {
      console.log('Loading more events...');
      const count = 20; // Number of additional events to fetch
      const data = await feedService.getNextEvents(count, recommId);
      setEvents((prevEvents) => [...prevEvents, ...data.recommendations]);
      setRecommId(data.recommId);
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
    <div className="container mx-auto py-8">
      <h2 className="text-2xl font-semibold mb-6">Explore Events</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {events.map((event) => (
          <EventCard key={event.eventId} event={event} recommId={recommId} />
        ))}
      </div>

      <div className="flex justify-center mt-10">
      {loading && <p>Loading more events...</p>}
      {!loading && recommId && (

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
  );
}

export default ExplorePage;

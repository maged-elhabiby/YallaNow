import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import EventCard from '../components/EventCard';
import FeedService from '../api/FeedService';
import { useAuth } from '../AuthContext';

const SearchPage = () => {
  const [events, setEvents] = useState([]);
  const [recommId, setRecommId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [searchParams] = useSearchParams();
  const searchQuery = searchParams.get('query');
  const { currentUser } = useAuth();
  const userId = currentUser?.uid;

  const searchEvents = async () => {
    if (!searchQuery) return;

    setLoading(true);
    try {
      const userId = userId; 
      const count = 20;
      const data = await FeedService.searchEvents(userId, count, searchQuery);
      setEvents(data.recommendations ?? []);
      setRecommId(data.recommId);
    } catch (error) {
      console.error('Error fetching search events:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    searchEvents();
  }, [searchQuery]);

  return (
    <div className="container mx-auto py-8">
      <h2 className="text-2xl font-semibold mb-6">Search Results</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {events.map((event) => (
          <EventCard key={event.eventId} event={event} recommId={recommId} />
        ))}
      </div>
      {loading && <div className="text-center mt-4">Loading...</div>}
    </div>
  );
};

export default SearchPage;

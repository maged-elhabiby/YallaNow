// EventsFeed.js
import React from 'react';
import EventCard from '../components/EventCard';

// Assuming this is wrapped in a parent container with a flex or grid class
const EventsFeed = ({ events }) => {
    return (
      // Apply a negative left margin to move the component more to the left
      <div className="ml-[-2rem] max-h-[calc(100vh-4rem)] overflow-auto">
          <div className="grid grid-cols-1 md:grid-cols-1 gap-4 p-4">
              {events.map((event) => (
                <EventCard key={event.eventId} event={event} />
              ))}
          </div>
      </div>
    );
  };
  

export default EventsFeed;

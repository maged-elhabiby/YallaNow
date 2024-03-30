/*
EventsFeed.js
EventsFeed component that displays a list of EventCard components.
*/
import React from 'react';
import EventCard from '../components/EventCard';

const EventsFeed = ({ events }) => {
    return (
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

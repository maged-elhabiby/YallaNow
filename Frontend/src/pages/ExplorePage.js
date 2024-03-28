import React, {useState} from 'react';
import EventCard from '../components/EventCard';

const ExplorePage = () => {

  // Temp data
  const events = [
    {
      eventId: 1,
      imageSrc: 'https://example.com/image1.jpg',
      imageAlt: 'Event 1',
      title: 'Event 1',
      eventStartDate: '2023-04-01',
      eventDescription: 'Description for Event 1',
    },
    {
      eventId: 2,
      eventImageUrl: 'https://example.com/image2.jpg',
      eventTitle: 'Event 2',
      eventStartDate: '2023-04-02',
      eventDescription: 'Description for Event 2',
    },
    {
      eventId: 3,
      eventImageUrl: 'https://example.com/image2.jpg',
      eventTitle: 'Event 3',
      eventStartDate: '2023-04-02',
      eventDescription: 'Description for Event 3',
    },
    {
      eventId: 4,
      eventImageUrl: 'https://example.com/image2.jpg',
      eventTitle: 'Event 4',
      eventStartDate: '2023-04-02',
      eventDescription: 'Description for Event 4',
    },
    {
      eventId: 5,
      eventImageUrl: 'https://example.com/image2.jpg',
      eventTitle: 'Event 5',
      eventStartDate: '2023-04-02',
      eventDescription: 'Description for Event 5',
    },
    {
      eventId: 6,
      eventImageUrl: 'https://example.com/image2.jpg',
      eventTitle: 'Event 6',
      eventStartDate: '2023-04-02',
      eventDescription: 'Description for Event 6',
    },
  ];

  return (
    <div className="container mx-auto py-8">
      <h2 className="text-2xl font-semibold mb-6">Explore Events</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {events.map((event) => (
          <EventCard key={event.eventId} event={event} />
        ))}
      </div>
    </div>
  );
}

export default ExplorePage;
// MyEventsPage.js
import React, { useEffect, useState } from 'react';
import MyCalendar from '../components/MyCalendar';
import EventsFeed from '../components/EventsFeed';
import getMockRecommendations from '../data/readRecommendationData';

const MyEventsPage = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        const formattedEvents = getMockRecommendations();
        setEvents(formattedEvents.recommendations);
    }, []);

    const eventsForCalendar = events.map(event => ({
        ...event,
        id: event.eventId.toString(),
        title: event.eventTitle,
        start: event.eventStartTime.toISOString(),
        end: event.eventEndTime.toISOString(),
        allDay: false,
    }));

    return (
        <div className="container mx-auto py-4 px-2">
            <h1 className="text-3xl font-bold mb-4 mt-2">My Events</h1>
            {/* Use Tailwind's grid system for side by side layout */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
                {/* Events Feed occupies 1 fraction of the space on large screens */}
                <div className="lg:col-span-1">
                    <h1 className="mt-2 mb-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-4xl">Your Events:</h1>
                    <EventsFeed events={events} />
                </div>
                {/* Calendar occupies the remaining 2 fractions of the space on large screens */}
                <div className="lg:col-span-2">
                    <MyCalendar events={eventsForCalendar} />
                </div>
            </div>
        </div>
    );
};

export default MyEventsPage;

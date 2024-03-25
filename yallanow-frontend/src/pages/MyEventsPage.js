// MyEventsPage.js
import React, { useEffect, useState } from 'react';
import MyCalendar from '../components/MyCalendar';
import EventsFeed from '../components/EventsFeed';
import getMockRecommendations from '../data/readRecommendationData'; // Assuming this path is correct

const MyEventsPage = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        // Directly set the formatted events data returned by getMockRecommendations
        const formattedEvents = getMockRecommendations();
        setEvents(formattedEvents.recommendations);
    }, []);

    // Adjust the transformEventsForCalendar function to work with the pre-formatted data
    const eventsForCalendar = events.map(event => ({
        id: event.eventId.toString(), // Ensure id is a string as FullCalendar expects
        title: event.eventTitle,
        start: event.eventStartTime.toISOString(), // Already a Date object, just convert to ISO string
        end: event.eventEndTime.toISOString(),
        allDay: false, // Adjust based on your requirements
    }));

    return (
        <div className="container mx-auto py-4 px-2">
            <h1 className="text-3xl font-bold mb-4">My Events</h1>
            <div className="flex flex-row -mx-2">
                <div className="flex-initial w-full lg:w-3/10 px-2 mb-4 lg:mb-0">
                    <div className="h-full">
                        <h2 className="text-xl font-semibold mb-4">Events Feed</h2>
                        {/* Pass the original array of events data to EventsFeed */}
                        <EventsFeed events={events} />
                    </div>
                </div>
                <div className="flex-grow w-full lg:w-7/10 px-2">
                    {/* Pass the transformed array of events data to MyCalendar */}
                    <MyCalendar events={eventsForCalendar} />
                </div>
            </div>
        </div>
    );
};

export default MyEventsPage;

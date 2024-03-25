// MyEventsPage.js
import React, { useEffect, useState } from 'react';
import MyCalendar from '../components/MyCalendar';
import EventsFeed from '../components/EventsFeed';
import getEvents from '../data/readRecommendationData';

const MyEventsPage = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        const fetchEvents = async () => {
            // Simulate fetching events data
            const eventsData = await getEvents();
            setEvents(eventsData);
        };
        fetchEvents();
    }, []);
    const myEvents = events ? transformEventsForCalendar(events.recommendations) : [];


    return (
        <div className="container mx-auto py-4 px-2"> {/* Reduced overall padding */}
            <h1 className="text-3xl font-bold mb-4">My Events</h1>
            <div className="flex flex-row -mx-2"> {/* Reduced negative margin for x-axis */}
                {/* Events Feed Component */}
                <div className="flex-initial w-full lg:w-3/10 px-2 mb-4 lg:mb-0"> {/* Reduced padding */}
                    <div className="h-full">
                        <h2 className="text-xl font-semibold mb-4">Events Feed</h2>
                        <EventsFeed events={events} />
                    </div>
                </div>
                {/* Calendar Component */}
                <div className="flex-grow w-full lg:w-7/10 px-2"> {/* Reduced padding */}
                    <MyCalendar events={myEvents} />
                </div>
            </div>
        </div>
    );
};
const transformEventsForCalendar = (recommendations) => {
    return recommendations?.map((recommendation) => { // Use optional chaining
        const { properties } = recommendation;
        return {
            id: recommendation.id,
            title: properties.eventTitle,
            start: new Date(properties.eventStartTime * 1000).toISOString(),
            end: new Date(properties.eventEndTime * 1000).toISOString(),
            url: properties.eventImageUrl,
            allDay: false,
        };
    });
};
export default MyEventsPage;

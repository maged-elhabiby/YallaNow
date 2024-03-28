import React, { useEffect, useState } from 'react';
import MyCalendar from '../components/MyCalendar';
import EventsFeed from '../components/EventsFeed';
import eventService from '../api/eventService';
import { useAuth } from '../AuthContext';

const MyEventsPage = () => {
    const [events, setEvents] = useState([]);
    const { currentUser } = useAuth();
    const userId = currentUser?.uid;

    useEffect(() => {
        const fetchEvents = async () => {
            // Replace this with the actual method to fetch user's RSVP'd events or another relevant method
            const fetchedEvents = await eventService.getUserRsvpdEvents(userId);
            setEvents(fetchedEvents);
        };

        fetchEvents();
    }, []);

    const eventsForCalendar = events.map(event => ({
        ...event,
        id: event.eventId.toString(),
        title: event.eventTitle,
        start: new Date(event.eventStartTime),
        end: new Date(event.eventEndTime),
        allDay: false,
    }));

    return (
        <div className="container mx-auto py-4 px-2">
            <h1 className="text-3xl font-bold mb-4 mt-2">My Events</h1>
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
                <div className="lg:col-span-1">
                    <h1 className="mt-2 mb-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-4xl">Your Events:</h1>
                    <EventsFeed events={events} />
                </div>
                <div className="lg:col-span-2">
                    <MyCalendar events={eventsForCalendar} />
                </div>
            </div>
        </div>
    );
};

export default MyEventsPage;

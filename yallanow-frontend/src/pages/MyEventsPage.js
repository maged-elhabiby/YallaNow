import React, { useEffect, useState } from 'react';
import MyCalendar from '../components/MyCalendar';
import EventsFeed from '../components/EventsFeed';
import eventService from '../api/eventService';
import { useAuth } from '../AuthContext';

const MyEventsPage = () => {
    const [events, setEvents] = useState([]);
    const { currentUser } = useAuth();
    const userId = currentUser?.uid;
    const [errorMessage, setErrorMessage] = useState('');
   
    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const fetchedEvents = await eventService.getUserRsvpdEvents(userId);
                if (fetchedEvents.length === 0) {
                    setErrorMessage('No Available Events'); // Set a default message for no events
                } else {
                    setEvents(fetchedEvents);
                }
            } catch (error) {
                if (error.response && error.response.status === 404) {
                    setErrorMessage('No Available Events');
                } else {
                    setErrorMessage('Error fetching events');
                }
                console.error("Error fetching user's RSVP'd events:", error);
            }
        };

        fetchEvents();
    }, [userId]);


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
                    {errorMessage ? (
                        <p className="text-xl text-red-500">{errorMessage}</p>
                    ) : (
                        <EventsFeed events={events} />
                    )}
                </div>
                <div className="lg:col-span-2">
                    {/* Calendar always shown but with conditional content based on events availability */}
                    <MyCalendar events={eventsForCalendar} />
                    {/* Optionally, display the error message or a "no events" message above or below the calendar */}
                    {errorMessage && (
                        <p className="text-center text-xl text-red-500 mt-4">{errorMessage}</p>
                    )}
                </div>
            </div>
        </div>
    );
};


export default MyEventsPage;

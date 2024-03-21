import React, { useState, useEffect } from 'react';

import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import './MyEvents.css'
import '../index.css'; // or import './tailwind.css'; if you created a separate file
import MyEventsFeed from './MyEventsFeed.js';
import eventData from './exampleResponse.json';

import Nav from './nav.js';
import MyCalendar from './MyCalendar.js';
const mockEvents = [
    { title: 'Event 1', date: '2024-03-22' },
    { title: 'Event 2', date: '2024-03-25' }
    // Add more mock events as needed
];

const MyEvents = () => {
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [modalIsOpen, setModalIsOpen] = useState(false);

    // Convert event data format if necessary
    const eventsForCalendar = eventData.events.map((event) => ({
        title: event.eventTitle,
        date: event.eventDate,
        // You can include more details as needed
    }));

    return (
        <body>
        {Nav()}
        <div className="App">
            <div className="flex">
            <div className="w-1/3 p-4 overflow-auto h-screen">
                    {/* Pass the original event data array to MyEventsFeed */}
                    <MyEventsFeed events={eventData.events} />
                </div>
                <div className="w-2/3 p-4">
                    {/* Pass the transformed event data for the calendar */}
                    <MyCalendar events={eventsForCalendar} />
                </div>
            </div>
        </div>
        </body>
    );
};
export default MyEvents;
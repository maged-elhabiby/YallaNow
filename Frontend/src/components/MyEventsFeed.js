// src/components/MyEventsFeed.js
import React from 'react';

const MyEventsFeed = ({ events }) => {
    return (
        <div className="overflow-auto h-full">
            <h2 className="text-xl font-bold mb-4">My Events</h2>
            <ul>
                {events.map((event) => (
                    <li key={event.eventID} className="mb-2 p-2 bg-white rounded-lg shadow">
                        <h3 className="font-bold">{event.eventTitle}</h3>
                        <p>{event.eventDescription}</p>
                        <p>Date: {event.eventDate}</p>
                        {/* Display more details as you see fit */}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default MyEventsFeed;

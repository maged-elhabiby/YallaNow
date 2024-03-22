import React, { useState, useEffect } from 'react';

import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import './MyEvents.css'
import '../index.css'; // or import './tailwind.css'; if you created a separate file
import MyEventsFeed from './MyEventsFeed.js';
import eventData from './exampleResponse.json';
import { Box, Card, CardContent, CardMedia, Typography, Modal } from '@mui/material';

import Nav from './nav.js';
import MyCalendar from './MyCalendar.js';
const mockEvents = [
    { title: 'Event 1', date: '2024-03-22' },
    { title: 'Event 2', date: '2024-03-25' }
    // Add more mock events as needed
];

const MyEvents = () => {
    // In MyEvents component
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [open, setOpen] = useState(false);

const handleEventClick = (eventClickInfo) => {
    console.log("Clicked event info:", eventClickInfo);
    const eventDetails = eventData.events.find(event => event.eventID === (eventClickInfo.event.id || event.eventID  ));
    console.log("Found event details:", eventDetails);
    if(eventDetails) {
        setSelectedEvent(eventDetails);
        setOpen(true);
    } else {
        console.error("Event details not found for:", eventClickInfo.event.title);
    }
};
const handleEventFeedClick = (event) => {
    console.log("Clicked event data:", event);
    setSelectedEvent(event);
    setOpen(true);
};

    const handleClose = () => {
        setOpen(false);
    };
    // Convert event data format if necessary
    const eventsForCalendar = eventData.events.map((event) => ({
        id: event.eventID,
        title: event.eventTitle,
        date: event.eventDate,
    }));

    return (
        <body>
        {Nav()}
        <div className="App">
            <div className="flex">
            <div className="w-1/3 p-4 overflow-auto h-screen">
                    {/* Pass the original event data array to MyEventsFeed */}
                    <MyEventsFeed events={eventData.events} onEventClick={handleEventFeedClick}/>
                </div>
                <div className="w-2/3 p-4">
                    {/* Pass the transformed event data for the calendar */}
                    <MyCalendar events={eventsForCalendar}  onEventClick={handleEventClick} />

                </div>
                <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
                    {/* Event Image */}
                    <CardMedia
                        component="img"
                        height="140"
                        image={selectedEvent?.imageURL || require('../party.png')}
                        alt="Event image"
                    />
                    {/* Event Title */}
                    <Typography id="modal-modal-title" variant="h6" component="h2" sx={{ mt: 2 }}>
                        {selectedEvent?.eventTitle}
                    </Typography>
                    {/* Event Description */}
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        {selectedEvent?.eventDescription}
                    </Typography>
                    {/* Event Date */}
                    <Typography sx={{ mt: 2 }}>
                        Date: {selectedEvent?.eventDate}
                    </Typography>
                    {/* Event Location */}
                    <Typography>
                        Location: {`${selectedEvent?.location.street}, ${selectedEvent?.location.city}, ${selectedEvent?.location.province}, ${selectedEvent?.location.country}`}
                    </Typography>
                    {/* Event Start Time */}
                    <Typography>
                        Start Time: {`${selectedEvent?.eventStartTime.hour}:${selectedEvent?.eventStartTime.minute}`}
                    </Typography>
                    {/* Event End Time */}
                    <Typography>
                        End Time: {`${selectedEvent?.eventEndTime.hour}:${selectedEvent?.eventEndTime.minute}`}
                    </Typography>
                    {/* Event Status */}
                    <Typography>
                        Status: {selectedEvent?.status}
                    </Typography>
                    {/* Event Capacity */}
                    <Typography>
                        Capacity: {selectedEvent?.capacity}
                    </Typography>
                </Box>
            </Modal>
            </div>
        </div>
        </body>
    );
};
export default MyEvents;
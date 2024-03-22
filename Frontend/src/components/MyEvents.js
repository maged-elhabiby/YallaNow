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
  <Box 
    sx={{ 
      position: 'absolute', 
      top: '50%', 
      left: '50%', 
      transform: 'translate(-50%, -50%)', 
      width: { xs: '90%', sm: 400 }, // Responsive width
      bgcolor: 'background.paper', 
      boxShadow: 24, 
      p: 4,
      borderRadius: 2, // Adds rounded corners
    }}
  >
    <CardMedia
      component="img"
      height="140"
      image={selectedEvent?.imageURL || require('../party.png')}
      alt="Event image"
      sx={{ borderRadius: 2 }} // Adds rounded corners to the image
    />
    <Typography id="modal-modal-title" variant="h6" component="h2" sx={{ mt: 2, fontWeight: 'bold' }}>
      {selectedEvent?.eventTitle}
    </Typography>
    <Typography sx={{ mt: 2 }}>
      <strong>Date:</strong> {selectedEvent?.eventDate}
    </Typography>
    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
      {selectedEvent?.eventDescription}
    </Typography>

    <Typography>
      <strong>Location:</strong> {`${selectedEvent?.location.street}, ${selectedEvent?.location.city}, ${selectedEvent?.location.province}, ${selectedEvent?.location.country}`}
    </Typography>
    <Typography>
      <strong>Start Time:</strong> {`${selectedEvent?.eventStartTime.hour}:${selectedEvent?.eventStartTime.minute}`}
    </Typography>
    <Typography>
      <strong>End Time:</strong> {`${selectedEvent?.eventEndTime.hour}:${selectedEvent?.eventEndTime.minute}`}
    </Typography>
    <Typography>
      <strong>Status:</strong> {selectedEvent?.status}
    </Typography>
    <Typography>
      <strong>Capacity:</strong> {selectedEvent?.capacity}
    </Typography>
  </Box>
</Modal>

            </div>
        </div>
        </body>
    );
};
export default MyEvents;
import React, {useState} from 'react';
import { Box, Card, CardContent, CardMedia, Typography, Modal } from '@mui/material';


const MyEventsFeed = ({ events }) => {
    const shortenText = (text, maxLength = 50) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength) + '...';
        }
        return text;
    };
    const [open, setOpen] = useState(false);
    const [selectedEvent, setSelectedEvent] = useState(null);
    const handleOpen = (event) => {
        setSelectedEvent(event);
        setOpen(true);
    };
    
    const handleClose = () => {
        setOpen(false);
    };
    
    return (
        <Box sx={{ width: '100%' }}>
            {events.map((event) => (
                <Card key={event.eventID} sx={{ mb: 2, maxWidth: 345 }} onClick={() => handleOpen(event)}>
                    <CardMedia
                        component="img"
                        sx={{ height: 140 }} // Adjust height as needed
                        image={event.imageURL || require('../party.png')} // Placeholder if no image URL
                        alt="Event image"
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                            {shortenText(event.eventTitle)}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {event.eventDate}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {shortenText(event.eventDescription, 100)}
                        </Typography>
                    </CardContent>
                </Card>
            ))}
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
        </Box>
    );
};

export default MyEventsFeed;

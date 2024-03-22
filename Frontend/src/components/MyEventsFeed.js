import React, {useState} from 'react';
import { Box, Card, CardContent, CardMedia, Typography, Modal } from '@mui/material';
import defaultImage from '../party.png';


const MyEventsFeed = ({ events,onEventClick }) => {
    const shortenText = (text, maxLength = 50) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength) + '...';
        }
        return text;
    };
    return (
        <Box sx={{ width: '100%' }}>
            {events.map((event) => (
                <Card key={event.eventID} sx={{ mb: 2, maxWidth: 345 }} onClick={() => onEventClick(event)}>
                    <CardMedia
                        component="img"
                        sx={{ height: 140 }} // Adjust height as needed
                        image={event.imageURL || defaultImage} // Placeholder if no image URL
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
        </Box>
    );
};

export default MyEventsFeed;

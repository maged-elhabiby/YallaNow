import axios from "axios";
const baseUrl = 'http://localhost:8082/microservice/events/';

const eventService = {


    createEvent: async (eventRequest) => {
        try {
            const response = await axios.post(baseUrl + 'AddEvent', eventRequest, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data;
        } catch (error) {
            if (error.response.status === 403) {
                console.error('Access denied:', error);
                throw new Error('Access denied');
            } else if (error.response.status === 400) {
                console.error('Bad request:', error);
                throw new Error(error.response.data);
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },

    updateEvent: async (eventRequest) => {
        try {
            const response = await axios.post(baseUrl + 'UpdateEvent', eventRequest, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data;
        } catch (error) {
            if (error.response.status === 403) {
                console.error('Access denied:', error);
                throw new Error('Access denied');
            } else if (error.response.status === 400) {
                console.error('Bad request:', error);
                throw new Error(error.response.data);
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },

    getEvent: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetEvent/' + eventId);
            return response.data;
        } catch (error) {
            if (error.response.status === 404) {
                console.error('Event not found:', error);
                throw new Error('Event not found');
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },

    getEventsForGroup: async (groupId) => {
        try {
            const response = await axios.get(baseUrl + 'GetGroupEvent/' + groupId);
            return response.data;
        } catch (error) {
            if (error.response.status === 404) {
                console.error('Event not found:', error);
                throw new Error('Event not found');
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },

    deleteEvent: async (eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteEvent/' + eventId);
            return response.data;
        } catch (error) {
            if (error.response.status === 403) {
                console.error('Access denied:', error);
                throw new Error('Access denied');
            } else if (error.response.status === 404) {
                console.error('Event not found:', error);
                throw new Error('Event not found');
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },


    getRsvpdUsersForEvent: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllEventParticipants/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },
    

    getUserRsvpdEvents: async (userId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllUserEvents/');
            return response.data.map(event=>eventService.formatEventForApp(event)) || []

        } catch (error) {
            if (error.response.status === 404) {
                console.error('Event not found:', error);
                throw new Error('Event not found');
            } else {
                console.error('Error fetching events:', error);
                throw error;
            }
        }
    },

    // remove partitcipant status from event
    unRsvpUserFromEvent: async (eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteParticipant/' + eventId);
            return true; // Indicate that the participant was successfully removed

        } catch (error) {
            if(error.response.status === 404){
                console.error('Participant not found:', error);
                throw new Error('User not part of Event');
            }else{
                console.error('Error removing participant from event:', error);
                return false; // Indicate that the participant was not removed
            }
        }
    },

    
    // get participant status from event

    isUserRsvpdToEvent: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetParticipantStatus/' + eventId);
            console.log(`RSVP status for user ${userId} in event ${eventId}:`, response.data);
            return true;

        } catch (error) {
            if(error.response.status === 404){
                console.error('Member is not registered in Event.', error);
                return false;
            }else{
                console.error('Error fetching RSVP status:', error);
                return false; // Assume the user has not RSVP'd
            }
        }
    },

    // Add participant to event
    rsvpUserToEvent: async (eventId) => {
        const request = {
            userId: null,
            eventId: eventId,
            participantStatus: "Attending"
        };

        try {
            const response = await axios.post(baseUrl + 'AddParticipant', request, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log('Participant added successfully');
            return true; // Indicate that the participant was successfully added
        } catch (error) {
            if(error.response.status === 422){
                console.error('Maximum capacity reached.');
                throw new Error('Maximum capacity reached.');
            }else if(error.response.status === 404){
                console.error('Event not found:', error);
                throw new Error('Event not found');
            }else{
                console.error('Error adding participant to event:', error);
                return false; // Indicate that the participant was not added
            }
        }
    },

    updateRsvpStatus: async (eventId, status) => {
        const request = {
            userId: null,
            eventId: eventId,
            participantStatus: status
        };

        try {
            const response = await axios.post(baseUrl + 'UpdateParticipant', request, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log('Participant status updated successfully');
            return true; // Indicate that the participant status was successfully updated
        } catch (error) {
            if(error.response.status === 404){
                console.error('Participant not found:', error);
                throw new Error('Participant not found');
            }else if(error.response.status === 422){
                console.error('Maximum capacity reached.');
                throw new Error('Maximum capacity reached.');
            }else{
                console.error('Error updating participant status:', error);
                return false; // Indicate that the participant status was not updated
            }
        }
    },


};

export default eventService;

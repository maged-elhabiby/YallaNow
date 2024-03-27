import axios from "axios";
const baseUrl = 'http://localhost:8080/microservice/events/';


const eventService = {

    addEvent: async (eventRequest) => {
        try {
            const response = await axios.post(baseUrl + 'AddEvent', eventRequest, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (response.status === 200) {
                return response.data || [];
            } else {

            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    updateEvent: async (eventRequest) => {
        try {
            const response = await axios.post(baseUrl + 'UpdateEvent', eventRequest, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (response.status === 200) {
                return response.data || [];
            } else {

            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getEventById: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetEvent/' + eventId);
            if (response.status === 200) {
                return response.data || {};
            } else {

            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getEventsForGroup: async (groupId) => {
        try {
            const response = await axios.get(baseUrl + 'GetGroupEvent/' + groupId);
            if (response.status === 200) {
                return response.data || [];
            } else {

            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    deleteEventById: async (eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteEvent/' + eventId);
            if (response.status === 200) {
                return true;
            } else {
                return false;
            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getRsvpdUsersForEvent: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllEventParticipants/' + eventId);
            if (response.status === 200) {
                return response.data || [];
            } else {

            }
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },
    

    getRsvpdEvents: async (userId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllUserEvents/' + userId);
            if (response.status === 200) {
                return response.data || [];
            } else {

            }
            
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    // remove partitcipant status from event
    unRsvpUserFromEvent: async (userId, eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteParticipant/' + userId + '/' + eventId);
            if (response.status === 200) {
                // The operation was successful
                console.log('Participant removed successfully');
                return true; // Indicate that the participant was successfully removed
            } else {
                // The operation failed
                console.error('Failed to remove participant:', response.status);
                return false; // Indicate that the participant was not removed
            }
        } catch (error) {
            console.error('Error removing participant from event:', error);
            return false; // Indicate that the participant was not removed
        }
    },

    
    // get participant status from event

    isUserRsvpdToEvent: async (userId, eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetParticipantStatus/' + userId + '/' + eventId);
            if (response.status === 200) {
                // The operation was successful
                console.log(`RSVP status for user ${userId} in event ${eventId}:`, response.data);
                return true; // User has RSVP'd
            } else {
                // The operation failed, or the status code is not 200
                console.error('Failed to fetch RSVP status:', response.status);
                return false; // Assume the user has not RSVP'd
            }
        } catch (error) {
            console.error('Error fetching RSVP status:', error);
            return false; // Assume the user has not RSVP'd
        }
    },

    // Add participant to event
    rsvpUserToEvent: async (userId, eventId) => {
        const request = {
            userId: userId,
            eventId: eventId,
            participantStatus: "Attending"
        };

        try {
            const response = await axios.post(baseUrl + 'AddParticipant', request, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (response.status === 200) {
                // The operation was successful
                console.log('Participant added successfully');
                return true; // Indicate that the participant was successfully added
            } else {
                // The operation failed
                console.error('Failed to add participant:', response.status);
                return false; // Indicate that the participant was not added
            }
        } catch (error) {
            console.error('Error adding participant to event:', error);
            return false; // Indicate that the participant was not added
        }
    },


};

export default eventService;

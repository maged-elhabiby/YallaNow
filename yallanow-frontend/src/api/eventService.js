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
            return response.data;
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
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getEvent: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetEvent/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getGroupEvent: async (groupId) => {
        try {
            const response = await axios.get(baseUrl + 'GetGroupEvent/' + groupId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    deleteEvent: async (eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteEvent/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },



    rsvpUserToEvent: async (userId, eventId) => {

    },

    getUserEvents: async (userId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllUserEvents/' + userId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    getEventUsers: async (eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllEventParticipants/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },
    


    updateUser: async (userRequest) => {
        try {
            const response = await axios.post(baseUrl + 'UpdateParticipant', userRequest, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },




    // remove partitcipant status from event
    removeRsvpStatusFromEvent: async (userId, eventId) => {
        try {
            const response = await axios.delete(baseUrl + 'DeleteParticipant/' + userId + '/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error removing participant from event:', error);
            throw error;
        }
    },

    // get participant status from event
    getUserRsvpStatusForEvent: async (userId, eventId) => {
        return 'Attending'
        try {
            const response = await axios.get(baseUrl + 'GetParticipantStatus/' + userId + '/' + eventId);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

    /*

    Participant dto
    participantID: int,
    userId: int,
    eventId: int,
    ParticipantStatus: ["Attending", "NotAttending", "Maybe"]

    */

    // Add participant to event
    addRspvStatusToEvent: async (userId, eventId) => {

        const request = {
            userId: userId,
            eventId: eventId,
            ParticipantStatus: "Attending"
        }

        try {
            const response = await axios.post(baseUrl + 'AddParticipant', request, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data;
        } catch (error) {
            console.error('Error adding participant to event:', error);
            throw error;
        }
    },

};

export default eventService;

const axios = require('axios');
const baseUrl = 'http://localhost:8082/microservice/events';

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

    addUserToEvent: async (eventRequest) => {
        try {
            const response = await axios.post(baseUrl + 'AddParticipant', eventRequest, {
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
    
    getUserEventStatus: async (userId, eventId) => {
        try {
            const response = await axios.get(baseUrl + 'GetParticipantStatus/' + userId + '/' + eventId);
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

    deleteUserFromEvent: async (userID, eventID) => {
        try {
            const response = await axios.get(baseUrl + 'GetAllEventParticipants/' + userID + '/' + eventID);
            return response.data;
        } catch (error) {
            console.error('Error fetching events:', error);
            throw error;
        }
    },

};

module.exports = eventService;

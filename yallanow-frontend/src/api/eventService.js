import axios from "axios";
const baseUrl = 'http://localhost:8080/microservice/events/';


const eventService = {


    convertEventDataToRequest: (data) => {
        return {
            eventID: data.eventId,
            groupID: data.gourpId,
            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,
            location: {
                street: data.eventLocaitonStreet,
                city: data.eventLocationCity,
                province: data.eventLocaitonProvince,
                country: data.eventLocaitonCountry
            },
            eventStartTime: data.eventStartTime,
            eventEndTime: data.eventEndTime,
            status: data.eventStatus,
            capacity: data.eventCapacity,
            count: data.eventAttendeeCount,
        }

    },

    convertResponseToEvent: (data) => {
        return {
            eventId: data.eventId,
            eventAttendeeCount: data.count,
            eventCapacity: data.capacity,
            eventDescription: data.eventDescription,
            eventEndTime: data.eventEndTime,
            eventImageUrl: data.imageUrl,
            eventLocationCity: data.address.street,
            eventLocationCountry: data.address.city,
            eventLocationProvince: data.address.province,
            eventLocationStreet: data.address.street,
            eventStartTime: data.eventStartTime,
            eventStatus: data.status,
            eventTitle: data.eventTitle,
            groupId: data.groupId
        }
    },

    createEvent: async (eventData) => {
        const request = convertToRequest(eventData)
    },

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

    getUserRsvpStatusForEvent: async (userId, eventId) => {
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

    
    /*

    Participant dto
    participantID: int,
    userId: int,
    eventId: int,
    ParticipantStatus: ["Attending", "NotAttending", "Maybe"]

    */

    // Add participant to event
    addRsvpStatusToEvent: async (userId, eventId) => {
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

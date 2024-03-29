import axios from "axios";
import { useAuth } from '../AuthContext';
const baseUrl = "http://34.120.232.193//events";


const eventServiceApi =  {

    
  createEvent: async (eventRequest) => {
    try {
        const response = await axios.post(baseUrl + "/AddEvent", eventRequest, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem('idToken')
            },
        });

        if (response.status === 200) {
            return response.data;
        } else if (response.status === 403) {
            throw new Error("Access denied");
        } else if (response.status === 400) {
            throw new Error("Bad request: " + response.data);
        } else {
            throw new Error("Error creating event");
        }
    } catch (error) {
        throw error;
    }
},

updateEvent: async (eventRequest) => {
    try {
        const response = await axios.post(baseUrl + "/UpdateEvent", eventRequest, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem('idToken')
            },
        });

        if (response.status === 200) {
            return response.data;
        } else if (response.status === 403) {
            throw new Error("Access denied");
        } else if (response.status === 400) {
            throw new Error("Bad request: " + response.data);
        } else {
            throw new Error("Error updating event");
        }
    } catch (error) {
        throw error;
    }
},

getEvent: async (eventId) => {
    try {
        const response = await axios.get(baseUrl + "/GetEvent/" + eventId);

        if (response.status === 200) {
            return response.data;
        } else if (response.status === 404) {
            throw new Error("Event not found");
        } else {
            throw new Error("Error fetching event");
        }
    } catch (error) {
        throw error;
    }
},

getEventsForGroup: async (groupId) => {
    try {
        const response = await axios.get(baseUrl + "/GetGroupEvents/" + groupId);

        if (response.status === 200) {
            return response.data;
        } else if (response.status === 404) {
            throw new Error("Events for group not found");
        } else {
            throw new Error("Error fetching events for group");
        }
    } catch (error) {
        throw error;
    }
},

deleteEvent: async (eventId) => {
    try {
        const response = await axios.delete(baseUrl + "/DeleteEvent/" + eventId);

        if (response.status === 200) {
            return response.data;
        } else if (response.status === 403) {
            throw new Error("Access denied");
        } else if (response.status === 404) {
            throw new Error("Event not found");
        } else {
            throw new Error("Error deleting event");
        }
    } catch (error) {
        throw error;
    }
},

getRsvpdUsersForEvent: async (eventId) => {
    try {
        const response = await axios.get(baseUrl + "/GetAllEventParticipants/" + eventId);

        if (response.status === 200) {
            return response.data;
        } else {
            throw new Error("Error fetching RSVP'd users for event");
        }
    } catch (error) {
        throw error;
    }
},

  getUserRsvpdEvents: async (currentUser) => {
    try {
        console.log(currentUser?.accessToken)
        const response = await axios.get(baseUrl + "/GetAllUserEvents", {
            
            headers: {
                "Id": currentUser?.uid,
                "Authorization": currentUser?.accessToken
            }
        });

        if (response.status === 200) {
            return response.data || [];
        } else if (response.status === 404) {
            throw new Error("User not found");
        } else {
            throw new Error("Error fetching user's RSVP'd events");
        }
    } catch (error) {
        throw error;
    }
},


unRsvpUserFromEvent: async (userId, eventId) => {
  try {
      const response = await axios.delete(baseUrl + "/DeleteParticipant/" + eventId, {
          data: { userId: userId }
      });

      if (response.status === 200) {
          console.log("Participant successfully removed");
          return true;
      } else if (response.status === 404) {
          throw new Error("Participant not found");
      } else {
          throw new Error("Error removing participant from event");
      }
  } catch (error) {
      throw error;
  }
},

isUserRsvpdToEvent: async (userId, eventId) => {
  try {
      const response = await axios.get(baseUrl + "/GetParticipantStatus/" + eventId);

      if (response.status === 200) {
          console.log(`RSVP status for user ${userId} in event ${eventId}:`, response.data);
          return true;
      } else if (response.status === 404) {
          throw new Error("Member is not registered in event");
      } else {
          throw new Error("Error fetching RSVP status");
      }
  } catch (error) {
      throw error;
  }
},

  // Add participant to event
  rsvpUserToEvent: async (userId, eventId) => {
    const request = {
        userId: userId,
        eventId: eventId,
        participantStatus: "Attending",
    };

      try {
          const response = await axios.post(baseUrl + "/AddParticipant", request, {
              headers: {
                  "Content-Type": "application/json",
              },
          });

          if (response.status === 200) {
              console.log("Participant added successfully");
              return true;
          } else if (response.status === 422) {
              throw new Error("Maximum capacity reached.");
          } else if (response.status === 404) {
              throw new Error("Event not found");
          } else {
              throw new Error("Error adding participant to event");
          }
      } catch (error) {
          throw error;
      }
  },

  updateRsvpStatus: async (userId, eventId, status) => {
    const request = {
        userId: userId,
        eventId: eventId,
        participantStatus: status,
    };

    try {
        const response = await axios.post(baseUrl + "/UpdateParticipant", request, {
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.status === 200) {
            console.log("Participant status updated successfully");
            return true;
        } else if (response.status === 422) {
            throw new Error("Maximum capacity reached.");
        } else if (response.status === 404) {
            throw new Error("Participant not found");
        } else {
            throw new Error("Error updating participant status");
        }
    } catch (error) {
        throw error;
    }
},
};

export default eventServiceApi;

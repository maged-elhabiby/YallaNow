import axios from "axios";
import config from "../config/config";
import {getAuth, getIdToken} from "firebase/auth";

class EventServiceApi {
    // Base URL for API calls, loaded from config and Initialize Firebase Auth
    constructor() {
        this.baseUrl = config.eventsBaseUrl;
        this.auth = getAuth();
    }

    // Fetches an ID token for the current user, throws error if not authenticated
    async fetchIdToken() {
        const user = this.auth.currentUser;
        if (!user) throw new Error("No authenticated user found");
        return getIdToken(user);
    }

    // Creates an event with provided data, handling authentication and response
    async createEvent(eventRequest) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(this.baseUrl + "/AddEvent", eventRequest, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Updates an existing event with new data, handling authentication and response
    async updateEvent(eventRequest) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(this.baseUrl + "/UpdateEvent", eventRequest, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Retrieves a single event by ID, handling authentication and response
    async getEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetEvent/" + eventId, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Fetches all events for a specific group, handling authentication and response
    async getEventsForGroup(groupId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetGroupEvents/" + groupId, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Deletes an event by ID, handling authentication and response
    async deleteEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.delete(this.baseUrl + "/DeleteEvent/" + eventId, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Retrieves all RSVP'd users for a specific event, handling authentication and response
    async getRsvpdUsersForEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetAllEventParticipants/" + eventId, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Fetches all events a specific user has RSVP'd to, handling authentication and response
    async getUserRsvpdEvents(currentUser) {
        try {
            const idToken = await this.fetchIdToken();
            console.log(idToken);
            const response = await axios.get(this.baseUrl + "/GetAllUserEvents", {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // UnRSVPs a user from an event, handling authentication and specific user data
    async unRsvpUserFromEvent(userId, eventId) {
        try {
            const idToken = await this.fetchIdToken();

            const response = await axios.delete(this.baseUrl + "/DeleteParticipant/" + eventId, {
                    headers: {
                        "Authorization": idToken
                    },
                },
                {
                    data: {userId: userId},
                });
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Checks if a user has RSVP'd to an event, handling authentication and response
    async isUserRsvpdToEvent(userId, eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetParticipantStatus/" + eventId, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // RSVPs a user to an event, handling user data and authentication
    async rsvpUserToEvent(userId, eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const request = {
                userId: userId,
                eventId: eventId,
                participantStatus: "Attending",
            };
            const response = await axios.post(this.baseUrl + "/AddParticipant", request, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    //  Updates the RSVP status of a user for an event, handling user data and authentication
    async updateRsvpStatus(userId, eventId, status) {
        try {
            const idToken = await this.fetchIdToken();
            const request = {
                userId: userId,
                eventId: eventId,
                participantStatus: status,
            };
            const response = await axios.post(this.baseUrl + "/UpdateParticipant", request, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    //  General method to handle API response, throws errors or returns data based on response status
    handleResponse(response, isBoolean = false) {
        if (isBoolean) {
            switch (response.status) {
                case 200:
                    return true;
                case 404:
                    return false;
                default:
                    throw new Error(response.data?.message || "Error processing request.");
            }
        }
        switch (response.status) {
            case 200:
                return response.data;
            case 400:
                throw new Error(response.data?.message || "Bad request.");
            case 403:
                throw new Error("Access denied.");
            case 404:
                throw new Error("Resource not found.");
            case 422:
                throw new Error(response.data?.message || "Maximum capacity reached.");
            default:
                throw new Error("Error processing request.");
        }
    }
}

export default new EventServiceApi();

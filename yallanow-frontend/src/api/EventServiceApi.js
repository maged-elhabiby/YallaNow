import axios from "axios";
import config from "../config/config";
import { getAuth,getIdToken  } from "firebase/auth";

class EventServiceApi {
    constructor() {
        this.baseUrl = config.eventsBaseUrl;
        this.auth = getAuth();
    }
    
    async fetchIdToken() {
        const user = this.auth.currentUser;
        if (!user) throw new Error("No authenticated user found");
        return getIdToken(user);
    }

    async createEvent(eventRequest) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(this.baseUrl + "/AddEvent", eventRequest, {
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async updateEvent(eventRequest) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(this.baseUrl + "/UpdateEvent", eventRequest, {
                    headers: { 
                        "Content-Type": "application/json",
                        "Authorization": idToken
                },    
                });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetEvent/" + eventId, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getEventsForGroup(groupId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetGroupEvents/" + groupId, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async deleteEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.delete(this.baseUrl + "/DeleteEvent/" + eventId, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getRsvpdUsersForEvent(eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetAllEventParticipants/" + eventId, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getUserRsvpdEvents(currentUser) {
        try {
            const idToken = await this.fetchIdToken();
            console.log(idToken);
            const response = await axios.get(this.baseUrl + "/GetAllUserEvents", {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async unRsvpUserFromEvent(userId, eventId) {
        try {
            const idToken = await this.fetchIdToken();
            
            const response = await axios.delete(this.baseUrl + "/DeleteParticipant/" + eventId, {
                headers: { 
                    "Authorization": idToken
            },    
            }, 
            {
                data: { userId: userId },
            });
            this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async isUserRsvpdToEvent(userId, eventId) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl + "/GetParticipantStatus/" + eventId, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

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
            this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async updateRsvpStatus(userId, eventId, status) {
        try {
            const idToken = await this.fetchIdToken();
            const request = {
                userId: userId,
                eventId: eventId,
                participantStatus: status,
            };
            const response = await axios.post(this.baseUrl + "/UpdateParticipant", request,{
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": idToken
            },
            });
            this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

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

import axios from "axios";
import config from "../config/config";
import { getAuth,getIdToken  } from "firebase/auth";

class GroupService {
    constructor() {
        this.auth = getAuth();
        this.baseUrl = config.groupsBaseUrl;
    }

    // Fetches Firebase ID token for the current user, required for authentication
    async fetchIdToken() {
        const user = this.auth.currentUser;
        if (!user) throw new Error("No authenticated user found");
        return getIdToken(user);
    }
    
    // Creates a new group with the provided group data, handling authentication
    async createGroup(groupData) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(this.baseUrl, groupData, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Retrieves a list of all groups, handling authentication
    async getGroups() {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(this.baseUrl, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Retrieves details for a specific group by ID, handling authentication
    async getGroup(groupID) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(`${this.baseUrl}/${groupID}`, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Updates details for a specific group by ID, handling authentication
    async updateGroup(groupID, groupData) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.put(`${this.baseUrl}/${groupID}`, groupData, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Deletes a specific group by ID, handling authentication
    async deleteGroup(groupID) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.delete(`${this.baseUrl}/${groupID}`, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Retrieves groups associated with a specific user ID, handling authentication
    async getGroupByUserID(userID) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(`${this.baseUrl}/user/${userID}`, {
                headers: { 
                    "Authorization": idToken
            },    
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    // Processes the server's response, parsing the response data or throwing errors
    handleResponse(response, isStatus = false) {
        switch (response.status) {
            case 200:
                return isStatus ? response.status : response.data;
            case 400:
                throw new Error("Bad request: " + response.data);
            case 403:
                throw new Error("Access denied.");
            case 404:
                throw new Error("Resource not found.");
            case 422:
                throw new Error("Maximum capacity reached.");
            default:
                throw new Error("Error processing request.");
        }
    }

}

export default new GroupService();

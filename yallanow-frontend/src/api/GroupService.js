import axios from "axios";
import config from "../config/config";
import { getAuth,getIdToken  } from "firebase/auth";

class GroupService {
    constructor() {
        this.auth = getAuth();
        this.baseUrl = config.groupsBaseUrl;
    }

    async fetchIdToken() {
        const user = this.auth.currentUser;
        if (!user) throw new Error("No authenticated user found");
        return getIdToken(user);
    }

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

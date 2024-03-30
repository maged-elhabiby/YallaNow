import axios from 'axios';
import config from '../config/config';
import {getAuth, getIdToken} from "firebase/auth";

class GroupMemberService {
    constructor() {
        this.auth = getAuth();
        this.baseUrl = config.groupsBaseUrl;
    }

    // Fetches ID token for the current user, throws an error if user not authenticated
    async fetchIdToken() {
        const user = this.auth.currentUser;
        if (!user) throw new Error("No authenticated user found");
        return getIdToken(user);
    }

    // Retrieves members of a specific group, handling authentication and response
    async getGroupMembers(groupID) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(`${this.baseUrl}/${groupID}/members`, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    // Adds a new member to a specific group, handling authentication and response
    async addGroupMember(groupID, memberData) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.post(`${this.baseUrl}/${groupID}/members`, memberData, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    // Removes a member from a specific group, handling authentication and logging
    async removeGroupMember(groupID, userID) {
        try {
            console.log("before syc")
            const idToken = await this.fetchIdToken();
            const response = await axios.delete(`${this.baseUrl}/${groupID}/members/${userID}`, {
                headers: {
                    "Authorization": idToken
                },
            });

            console.log("success")
            return this.handleResponse(response, true);

        } catch (error) {
            console.error('Server response:', error.response);
            throw new Error('Error communicating with server.');
        }
    }

    // Retrieves information of a specific group member, handling authentication and response
    async getGroupMember(groupID, userID) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.get(`${this.baseUrl}/${groupID}/members/${userID}`, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    // Updates information for a member of a specific group, handling authentication and response
    async updateGroupMember(groupID, userID, memberData) {
        try {
            const idToken = await this.fetchIdToken();
            const response = await axios.put(`${this.baseUrl}/${groupID}/members/${userID}`, memberData, {
                headers: {
                    "Authorization": idToken
                },
            });
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    // Handles responses from the server, parsing the response and throwing errors as needed
    handleResponse(response) {
        switch (response.status) {
            case 200:
                return response.data;
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

export default new GroupMemberService();

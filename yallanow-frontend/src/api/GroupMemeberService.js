import axios from 'axios';
import config from '../config/config';

class GroupMemberService {
    constructor() {
        axios.defaults.headers.common["Authorization"] = localStorage.getItem("idToken");
        this.baseUrl = config.groupsBaseUrl;
    }

    async getGroupMembers(groupID) {
        try {
            const response = await axios.get(`${this.baseUrl}${groupID}/members`);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    async addGroupMember(groupID, memberData) {
        try {
            const response = await axios.post(`${this.baseUrl}${groupID}/members`, memberData);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    async removeGroupMember(groupID, userID) {
        try {
            const response = await axios.delete(`${this.baseUrl}${groupID}/members/${userID}`);
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    async getGroupMember(groupID, userID) {
        try {
            const response = await axios.get(`${this.baseUrl}${groupID}/members/${userID}`);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

    async updateGroupMember(groupID, userID, memberData) {
        try {
            const response = await axios.put(`${this.baseUrl}${groupID}/members/${userID}`, memberData);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error('Error communicating with server.');
        }
    }

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

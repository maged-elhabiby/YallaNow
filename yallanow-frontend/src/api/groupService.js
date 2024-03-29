import axios from "axios";
import config from "../config/config";

class GroupService {
    constructor() {
        this.baseUrl = config.groupsBaseUrl;
    }

    async createGroup(groupData) {
        try {
            const response = await axios.post(this.baseUrl, groupData);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getGroups() {
        try {
            const response = await axios.get(this.baseUrl);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getGroup(groupID) {
        try {
            const response = await axios.get(`${this.baseUrl}/${groupID}`);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async updateGroup(groupID, groupData) {
        try {
            const response = await axios.put(`${this.baseUrl}/${groupID}`, groupData);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async deleteGroup(groupID) {
        try {
            const response = await axios.delete(`${this.baseUrl}/${groupID}`);
            return this.handleResponse(response, true);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    async getGroupByUserID(userID) {
        try {
            const response = await axios.get(`${this.baseUrl}/user/${userID}`);
            return this.handleResponse(response);
        } catch (error) {
            throw new Error("Error communicating with server.");
        }
    }

    handleResponse(response, isStatus = false) {
        if (response.status === 200) {
            return isStatus ? response.status : response.data;
        } else if (response.status === 404) {
            throw new Error("Resource not found.");
        } else {
            throw new Error("Error processing request.");
        }
    }
}

export default new GroupService();

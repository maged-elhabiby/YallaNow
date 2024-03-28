import axios from 'axios';

const baseUrl = 'http://localhost:8080/microservice/groups/';

const groupService = {
    createGroup: async (groupData) => {
        try {
            const response = await axios.post(baseUrl, groupData);
            return response.data;
        } catch (error) {
            console.error('Error creating group:', error);
            throw error;
        }
    },
    getGroups: async () => {
        try {
            const response = await axios.get(baseUrl);
            return response.data;
        } catch (error) {
            console.error('Error getting groups:', error);
            throw error;
        }
    },
    getGroup: async (groupID) => {
        try {
            const response = await axios.get(`${baseUrl}${groupID}`);
            return response.data;
        } catch (error) {
            console.error(`Error getting group with ID ${groupID}:`, error);
            throw error;
        }
    },
    updateGroup: async (groupID, groupData) => {
        try {
            const response = await axios.put(`${baseUrl}${groupID}`, groupData);
            return response.data;
        } catch (error) {
            console.error(`Error updating group with ID ${groupID}:`, error);
            throw error;
        }
    },
    deleteGroup: async (groupID) => {
        try {
            const response = await axios.delete(`${baseUrl}${groupID}`);
            return response.status;
        } catch (error) {
            console.error(`Error deleting group with ID ${groupID}:`, error);
            throw error;
        }
    },
    getGroupByUserID: async (userID) => {
        try {
            const response = await axios.get(`${baseUrl}user/${userID}`);
            return response.data;
        } catch (error) {
            console.error(`Error getting groups for user ${userID}:`, error);
            throw error;
        }
    }
};

export default groupService;

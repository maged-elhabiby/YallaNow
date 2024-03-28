import axios from 'axios';

const baseUrl = 'http://localhost:8080/microservice/groups/';

const groupMemberService = {
    getGroupMembers: async (groupID) => {
        try {
            const response = await axios.get(`${baseUrl}${groupID}/members`);
            return response.data;
        } catch (error) {
            console.error(`Error getting members of group ${groupID}:`, error);
            throw error;
        }
    },
    addGroupMember: async (groupID, memberData) => {
        try {
            const response = await axios.post(`${baseUrl}${groupID}/members`, memberData);
            return response.data;
        } catch (error) {
            console.error(`Error adding member to group ${groupID}:`, error);
            throw error;
        }
    },
    removeGroupMember: async (groupID, userID) => {
        try {
            const response = await axios.delete(`${baseUrl}${groupID}/members/${userID}`);
            return response.status;
        } catch (error) {
            console.error(`Error removing member ${userID} from group ${groupID}:`, error);
            throw error;
        }
    },
    getGroupMember: async (groupID, userID) => {
        try {
            const response = await axios.get(`${baseUrl}${groupID}/members/${userID}`);
            return response.data;
        } catch (error) {
            console.error(`Error getting member ${userID} from group ${groupID}:`, error);
            throw error;
        }
    },
    updateGroupMember: async (groupID, userID, memberData) => {
        try {
            const response = await axios.put(`${baseUrl}${groupID}/members/${userID}`, memberData);
            return response.data;
        } catch (error) {
            console.error(`Error updating member ${userID} in group ${groupID}:`, error);
            throw error;
        }
    },
    
};

export default groupMemberService;

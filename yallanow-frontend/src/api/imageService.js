

import axios from 'axios';

const baseUrl = 'http://localhost:8082/microservice/images/';

const imageService = {
    uploadImageByBase64: async (base64Image) => {
        try {
            const response = await axios.post(baseUrl + 'UploadImage', { base64Image });
            return response.data;
        } catch (error) {
            console.error('Error uploading image:', error);
            throw error;
        }
    },

    getImageUrlById: async (imageId) => {
        try {
            const response = await axios.get(baseUrl + 'GetImage/' + imageId);
            return response.data.imageLink;
        } catch (error) {
            console.error('Error getting image URL:', error);
            throw error;
        }
    },

    updateImage: async (imageId, imageLink) => {
        try {
            const response = await axios.post(baseUrl + 'UpdateImage', { imageId, imageLink });
            return response.data;
        } catch (error) {
            console.error('Error updating image:', error);
            throw error;
        }
    },

    deleteImage: async (imageId) => {
        try {
            const response = await axios.get(baseUrl + 'DeleteImage/' + imageId);
            return response.data;
        } catch (error) {
            console.error('Error deleting image:', error);
            throw error;
        }
    }
};

export default imageService;

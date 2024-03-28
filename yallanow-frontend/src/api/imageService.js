const baseUrl = 'http://localhost:8082/microservice/images/';
const axios = require('axios');
const cloudinaryCloudName = 'dt8r2amry';
const cloudinaryUploadPreset = 'yallaNow';

const imageService = {

    uploadImageByBase64: async (base64Image) => {
        try {
            const formData = new FormData();
            formData.append('file', base64Image);
            formData.append('upload_preset', cloudinaryUploadPreset);

            const config = {
                headers: {
                    'content-type': 'multipart/form-data'
                }
            };

            const cloudinary = await axios.post(`https://api.cloudinary.com/v1_1/${cloudinaryCloudName}/image/upload`, formData, config);
            const response = await axios.post(baseUrl + 'AddImage', { imageLink: cloudinary.data.secure_url });

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

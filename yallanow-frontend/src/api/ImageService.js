import axios from 'axios';

const baseUrl = 'http://localhost:8082/microservice/images/';
const cloudinaryCloudName = 'dt8r2amry';
const cloudinaryUploadPreset = 'yallaNow';

const imageService = {
    // Uploads an image to Cloudinary and then saves its URL to the local service

    uploadImage: async (base64Image) => {
        try {
            // Prepare form data for Cloudinary
            const formData = new FormData();
            formData.append('file', base64Image);
            formData.append('upload_preset', cloudinaryUploadPreset);

            // Configuration for multipart/form-data header
            const config = {
                headers: {
                    'content-type': 'multipart/form-data'
                }
            };

            // Upload image to Cloudinary
            const cloudinary = await axios.post(`https://api.cloudinary.com/v1_1/${cloudinaryCloudName}/image/upload`, formData, config);

            // Save uploaded image URL to local service
            const response = await axios.post(baseUrl + 'AddImage', {imageLink: cloudinary.data.secure_url});

            return response.data;
        } catch (error) {
            console.error('Error uploading image:', error);
            throw error;
        }
    },

    // Retrieves the URL of an image by its ID from the local service
    getImageUrlById: async (imageId) => {
        try {
            const response = await axios.get(baseUrl + 'GetImage/' + imageId);
            return response.data.imageLink;
        } catch (error) {
            console.error('Error getting image URL:', error);
            throw error;
        }
    },

    // Updates the URL of an existing image in the local service
    updateImage: async (imageId, imageLink) => {
        try {
            const response = await axios.post(baseUrl + 'UpdateImage', {imageId, imageLink});
            return response.data;
        } catch (error) {
            console.error('Error updating image:', error);
            throw error;
        }
    },

    // Deletes an image by its ID from the local service
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

import eventService from "./eventService";
import feedService from "./feedService"
import imageService from "./imageService"

const EventApi = {

    createEvent: async (eventData) => {

        const imageData = await imageService.uploadImageByBase64(eventData.imageBase64);
        
        const eventImageUrl = imageData.imageUrl;
        eventData.imageId = imageData.id;
        const requestData = EventApi.formatEventForEventService(eventData);

        const rawEvent = await eventService.createEvent(requestData);
        const formattedEvent = EventApi.formatEventForEventService(rawEvent);
        formattedEvent.eventImageUrl = eventImageUrl;

        return formattedEvent;
    },

    
    // updateEvent: async (eventData) => {
    //     const rawEvent = eventService.updateEvent(eventData);
    //     const formattedEvent = EventApi.formatEventForEventService(rawEvent);
    //     formattedEvent.eventImageUrl = await imageService.getImageUrlById(formattedEvent.eventId);
    
    //     return formattedEvent;
    // },


    getEventById: async (eventId) => {
        const rawEvent = await eventService.getEventById(eventId);
        const formattedEvent = EventApi.formatEventForEventService(rawEvent);
        formattedEvent.eventImageUrl = await imageService.getImageUrlById(formattedEvent.imageId);
        
        return formattedEvent;
    },

    deleteEventById: async (eventId) => {
        return await eventService.deleteEvent(eventId);
    },

    getRsvpdUsersForEvent: async (eventId) => {
        return await eventService.getRsvpdUsersForEvent(eventId);
    },

    getEventsForGroup: async (groupId) => {
        const rawEvents = await eventService.getEventsForGroup(groupId);
        const eventsWithImages = await Promise.all(rawEvents.map(async (event) => {
            const formattedEvent = EventApi.formatEventFromEventService(event);
            formattedEvent.eventImageUrl = await imageService.getImageUrlById(event.imageId);
            return formattedEvent;
        }));
    
        return eventsWithImages;
    },

    getRsvpdEvents: async (userId) => {
        const response = await eventService.getRsvpdEvents(userId);
        const eventsWithImages = await Promise.all(response.map(async (data) => {
            const formattedEvent = EventApi.formatEventFromEventService(data.event);
            formattedEvent.eventImageUrl = await imageService.getImageUrlById(data.event.imageId);
            return formattedEvent;
        }));
    
        return eventsWithImages;
    },

    isUserRsvpdToEvent: async (userId, eventId) => {
        return eventService.isUserRsvpdToEvent(userId, eventId);
    },

    unRsvpUserFromEvent: async (userId, eventId) => {
        return eventService.unRsvpUserFromEvent(userId, eventId);
    },

    rsvpUserToEvent: async (userId, eventId) => {
        return eventService.rsvpUserToEvent(userId, eventId);
    },

    getHomepageEvents: async (userId, count) => {
        return await feedService.getHomepageEvents(userId, count);
    },

    getNextEvents: async (count, recommId) => {
        return await feedService.getNextEvents(count, recommId);
    },

    searchEvents: async (userId, count, searchQuery) => {
        return await feedService.searchEvents(userId, count, searchQuery)
    },

    getImageUrlById: (imageId) => {
        return imageService.getImageUrlById(imageId);
    },

    formatEventForEventService: (data) => {
        return {
            eventID: data.eventId,
            groupID: data.groupId,
            imageID: data.imageId,

            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,
            location: {
                street: data.eventLocaitonStreet,
                city: data.eventLocationCity,
                province: data.eventLocaitonProvince,
                country: data.eventLocaitonCountry
            },
            eventStartTime: data.eventStartTime,
            eventEndTime: data.eventEndTime,
            status: data.eventStatus,
            capacity: data.eventCapacity,
            count: data.eventAttendeeCount,
            
        }

    },

    formatEventFromEventService: (data) => {
        return {
            eventId: data.eventId,
            eventAttendeeCount: data.count,
            eventCapacity: data.capacity,
            eventDescription: data.eventDescription,
            eventEndTime: data.eventEndTime,
            eventImageId: data.imageId,
            eventLocationCity: data.address.street,
            eventLocationCountry: data.address.city,
            eventLocationProvince: data.address.province,
            eventLocationStreet: data.address.street,
            eventStartTime: data.eventStartTime,
            eventStatus: data.status,
            eventTitle: data.eventTitle,
            groupId: data.groupId,
            eventImageUrl: null,
            imageId: data.imageId
        }
    },
    
}

export default EventApi;
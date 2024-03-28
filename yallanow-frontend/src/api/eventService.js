import eventServiceApi from "./eventServiceApi";
import imageService from "./imageService";

const eventService = {
    createEvent: async (eventData) => {
        const imageData = await imageService.uploadImage(eventData.imageBase64);
        eventData.imageId = imageData.id;
        const eventImageUrl = imageData.imageUrl;

        const requestData = eventServiceApi.formatEventForEventService(eventData);
        const rawEvent = await eventServiceApi.createEvent(requestData);
        const formattedEvent = eventServiceApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = eventImageUrl;

        return formattedEvent;
    },

    updateEvent: async (eventData) => {
        let eventImageUrl = null;
        if (eventData.imageBase64) {
            const imageData = await imageService.uploadImage(eventData.imageBase64);
            eventImageUrl = imageData.imageUrl;
        }

        const requestData = eventServiceApi.formatEventForEventService(eventData);
        const rawEvent = await eventServiceApi.updateEvent(requestData);
        const formattedEvent = eventServiceApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = eventImageUrl;

        return formattedEvent;
    },

    getEvent: async (eventId) => {
        const rawEvent = await eventServiceApi.getEvent(eventId);
        const formattedEvent = eventServiceApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = await imageService.getImageUrlById(formattedEvent.imageId);

        return formattedEvent;
    },

    getEventsForGroup: async (groupId) => {
        const rawEvents = await eventServiceApi.getEventsForGroup(groupId);
        const eventsWithImages = await Promise.all(rawEvents.map(async (event) => {
            const formattedEvent = eventServiceApi.formatEventFromEventService(event);
            formattedEvent.eventImageUrl = await imageService.getImageUrlById(event.imageId);
            return formattedEvent;
        }));

        return eventsWithImages;
    },

    deleteEvent: async (eventId) => {
        return await eventServiceApi.deleteEvent(eventId);
    },

    getRsvpdUsersForEvent: async (eventId) => {
        return await eventServiceApi.getRsvpdUsersForEvent(eventId);
    },

    getUserRsvpdEvents: async (userId) => {
        const rawEvents = await eventServiceApi.getUserRsvpdEvents(userId);
        const eventsWithImages = await Promise.all(rawEvents.map(async (event) => {
            const formattedEvent = eventServiceApi.formatEventFromEventService(event);
            formattedEvent.eventImageUrl = await imageService.getImageUrlById(event.imageId);
            return formattedEvent;
        }));

        return eventsWithImages;
    },

    unRsvpUserFromEvent: async (userId, eventId) => {
        return await eventServiceApi.unRsvpUserFromEvent(userId, eventId);
    },

    isUserRsvpdToEvent: async (userId, eventId) => {
        return await eventServiceApi.isUserRsvpdToEvent(userId, eventId);
    },

    rsvpUserToEvent: async (userId, eventId) => {
        return await eventServiceApi.rsvpUserToEvent(userId, eventId);
    },

    updateRsvpStatus: async (userId, eventId, status) => {
        return await eventServiceApi.updateRsvpStatus(userId, eventId, status);
    },

    formatEventForEventService: (data) => {
        return {
            eventID: data.eventId,
            groupID: data.groupId,
            imageID: data.imageId,
            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,
            location: data.location,
            eventStartTime: data.eventStartTime,
            eventEndTime: data.eventEndTime,
            status: data.eventStatus,
            capacity: data.eventCapacity,
            count: data.eventAttendeeCount,
            imageURL: data.eventImageUrl,
        };
    },

    formatEventFromEventService: (data) => {
        return {
            eventId: data.eventID,
            groupId: data.groupID,
            imageId: data.imageID,
            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,
            location: data.location,
            eventStartTime: data.eventStartTime,
            eventEndTime: data.eventEndTime,
            eventStatus: data.status,
            eventCapacity: data.capacity,
            eventAttendeeCount: data.count,
            eventImageUrl: null,
        };
    },
};

export default eventService;

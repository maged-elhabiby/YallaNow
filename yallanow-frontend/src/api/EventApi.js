import eventService from "./eventService";
import imageService from "./imageService";

const EventApi = {
    createEvent: async (eventData) => {
        const imageData = await imageService.uploadImageByBase64(eventData.imageBase64);
        eventData.imageId = imageData.id;
        const eventImageUrl = imageData.imageUrl;

        const requestData = EventApi.formatEventForEventService(eventData);
        const rawEvent = await eventService.createEvent(requestData);
        const formattedEvent = EventApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = eventImageUrl;

        return formattedEvent;
    },

    updateEvent: async (eventData) => {
        let eventImageUrl = null;
        if (eventData.imageBase64) {
            const imageData = await imageService.uploadImageByBase64(eventData.imageBase64);
            eventData.imageId = imageData.id;
            eventImageUrl = imageData.imageUrl;
        } else {
            eventImageUrl = await imageService.getImageUrlById(eventData.imageId);
        }

        const requestData = EventApi.formatEventForEventService(eventData);
        const rawEvent = await eventService.updateEvent(requestData);
        const formattedEvent = EventApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = eventImageUrl;

        return formattedEvent;
    },

    getEvent: async (eventId) => {
        const rawEvent = await eventService.getEvent(eventId);
        const formattedEvent = EventApi.formatEventFromEventService(rawEvent);
        formattedEvent.eventImageUrl = await imageService.getImageUrlById(formattedEvent.imageId);

        return formattedEvent;
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

    deleteEvent: async (eventId) => {
        return await eventService.deleteEvent(eventId);
    },

    getRsvpdUsersForEvent: async (eventId) => {
        return await eventService.getRsvpdUsersForEvent(eventId);
    },

    getUserRsvpdEvents: async (userId) => {
        const rawEvents = await eventService.getUserRsvpdEvents(userId);
        const eventsWithImages = await Promise.all(rawEvents.map(async (event) => {
            const formattedEvent = EventApi.formatEventFromEventService(event);
            formattedEvent.eventImageUrl = await imageService.getImageUrlById(event.imageId);
            return formattedEvent;
        }));

        return eventsWithImages;
    },

    unRsvpUserFromEvent: async (userId, eventId) => {
        return await eventService.unRsvpUserFromEvent(userId, eventId);
    },

    isUserRsvpdToEvent: async (userId, eventId) => {
        return await eventService.isUserRsvpdToEvent(userId, eventId);
    },

    rsvpUserToEvent: async (userId, eventId) => {
        return await eventService.rsvpUserToEvent(userId, eventId);
    },

    updateRsvpStatus: async (userId, eventId, status) => {
        return await eventService.updateRsvpStatus(userId, eventId, status);
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

export default EventApi;

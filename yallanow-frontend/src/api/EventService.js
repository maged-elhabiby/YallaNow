import EventServiceApi from "./EventServiceApi";
import ImageService from "./ImageService";

class EventService {

    async createEvent(eventData) {

        const requestData = this.formatEventForEventService(eventData);
        const rawEvent = await EventServiceApi.createEvent(requestData);
        return this.formatEventFromEventService(rawEvent);
    }

    async updateEvent(eventData) {
        const requestData = this.formatEventForEventService(eventData);
        const rawEvent = await EventServiceApi.updateEvent(requestData);
        return this.formatEventFromEventService(rawEvent);
    }

    async getEvent(eventId) {
        const rawEvent = await EventServiceApi.getEvent(eventId);
        return this.formatEventFromEventService(rawEvent);
    }

    async getEventsForGroup(groupId) {
        const rawEvents = await EventServiceApi.getEventsForGroup(groupId);
        return rawEvents.map(async (event) => this.formatEventFromEventService(event));
    }

    async deleteEvent(eventId) {
        return await EventServiceApi.deleteEvent(eventId);
    }

    async getRsvpdUsersForEvent(eventId) {
        return await EventServiceApi.getRsvpdUsersForEvent(eventId);
    }

    async getUserRsvpdEvents(userId) {
        const rawEvents = await EventServiceApi.getUserRsvpdEvents(userId);
        return rawEvents.map(async (event) => this.formatEventFromEventService(event));
    }

    async unRsvpUserFromEvent(userId, eventId) {
        return await EventServiceApi.unRsvpUserFromEvent(userId, eventId);
    }

    async isUserRsvpdToEvent(userId, eventId) {
        return await EventServiceApi.isUserRsvpdToEvent(userId, eventId);
    }

    async rsvpUserToEvent(userId, eventId) {
        return await EventServiceApi.rsvpUserToEvent(userId, eventId);
    }

    async updateRsvpStatus(userId, eventId, status) {
        return await EventServiceApi.updateRsvpStatus(userId, eventId, status);
    }

    formatEventForEventService(data) {
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
    }

    formatEventFromEventService(data, imageUrl) {
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
            eventImageUrl: imageUrl,
        };
    }
}

export default new EventService();

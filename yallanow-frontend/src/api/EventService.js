import EventServiceApi from "./EventServiceApi";

class EventService {

    async createEvent(eventData) {
        eventData.eventImageUrl = "https://storage.googleapis.com/tmp-bucket-json-data/500x500.jpg"
        const requestData = this.formatEventForEventService(eventData);
        console.log(requestData);
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
            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,
            location: {
                addressID: 1,
                street: data.eventLocationStreet,
                city: data.eventLocationCity,
                province: data.eventLocationProvince,
                country: data.eventLocationCountry,
                postalCode: data.eventLocationPostalCode,
            },
            eventStartTime: data.eventStartTime,
            eventEndTime: data.eventEndTime,
            status: data.eventStatus,
            capacity: 1,
            count: 1,

            imageUrl: data.imageUrl,
        };
    }

    formatEventFromEventService(data, imageUrl) {
        return {
            eventId: data.eventID,
            groupId: data.groupID,
            eventTitle: data.eventTitle,
            eventDescription: data.eventDescription,

            eventLocationStreet: data.location.street,
            eventLocationCity: data.location.city,
            eventLocationProvince: data.location.province,
            eventLocationCountry:data.location.country,
            eventLocationPostalCode: data.location.postalCode,

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

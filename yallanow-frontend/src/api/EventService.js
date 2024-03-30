import EventServiceApi from "./EventServiceApi";

class EventService {

    // Creates a new event with predefined image URL, formats data for sending and receiving.
    async createEvent(eventData) {
        eventData.eventImageUrl = "https://storage.googleapis.com/tmp-bucket-json-data/500x500.jpg"
        const requestData = this.formatEventForEventService(eventData);
        console.log(requestData);
        const rawEvent = await EventServiceApi.createEvent(requestData);
        return this.formatEventFromEventService(rawEvent);
    }

   // Updates an existing event by formatting and sending data, then formatting the response.
    async updateEvent(eventData) {
        const requestData = this.formatEventForEventService(eventData);
        const rawEvent = await EventServiceApi.updateEvent(requestData);
        return this.formatEventFromEventService(rawEvent);
    }

    // Retrieves a single event by its ID and formats the received data.
    async getEvent(eventId) {
        const rawEvent = await EventServiceApi.getEvent(eventId);
        return this.formatEventFromEventService(rawEvent);
    }

    // Fetches events for a specific group and formats each received event.
    async getEventsForGroup(groupId) {
        const rawEvents = await EventServiceApi.getEventsForGroup(groupId);
        return rawEvents.map(async (event) => this.formatEventFromEventService(event));
    }

    // Deletes an event by its ID.
    async deleteEvent(eventId) {
        return await EventServiceApi.deleteEvent(eventId);
    }

    // Retrieves users who RSVP'd to a specific event.
    async getRsvpdUsersForEvent(eventId) {
        return await EventServiceApi.getRsvpdUsersForEvent(eventId);
    }

    // Fetches events a user has RSVP'd to and formats each received event.
    async getUserRsvpdEvents(userId) {
        const rawEvents = await EventServiceApi.getUserRsvpdEvents(userId);
        return rawEvents.map(async (event) => this.formatEventFromEventService(event));
    }

    // Removes a user's RSVP from an event.
    async unRsvpUserFromEvent(userId, eventId) {
        return await EventServiceApi.unRsvpUserFromEvent(userId, eventId);
    }

    // Checks if a user has RSVP'd to an event.
    async isUserRsvpdToEvent(userId, eventId) {
        return await EventServiceApi.isUserRsvpdToEvent(userId, eventId);
    }

    // RSVPs a user to an event.
    async rsvpUserToEvent(userId, eventId) {
        return await EventServiceApi.rsvpUserToEvent(userId, eventId);
    }

    // Updates a user's RSVP status for an event.
    async updateRsvpStatus(userId, eventId, status) {
        return await EventServiceApi.updateRsvpStatus(userId, eventId, status);
    }

    // Formats event data before sending it to the event service.
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

    // Formats the event data received from the event service.
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

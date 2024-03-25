import React from 'react';

const EventCard = ({ event }) => {
  return (
    <div key={event.eventId} className="group relative">
      <div className="aspect-h-3 aspect-w-4 overflow-hidden rounded-lg bg-gray-100">
        <img src={event.eventImageUrl} className="object-cover object-center" />
        <div className="flex items-end p-4 opacity-0 group-hover:opacity-100" aria-hidden="true">
          <div className="w-full rounded-md bg-white bg-opacity-75 px-4 py-2 text-center text-sm font-medium text-gray-900 backdrop-blur backdrop-filter">
            View Event
          </div>
        </div>
      </div>
      <div className="mt-4 flex items-center justify-between space-x-8 text-base font-medium text-gray-900">
        <h3>
          <a href="#">
            <span aria-hidden="true" className="absolute inset-0" />
            {event.eventTitle}
          </a>
        </h3>
        <p>{event.eventStartDate}</p>
      </div>
      <p className="mt-1 text-sm text-gray-500 truncate">{event.eventDescription}</p>
    </div>
  );
}

export default EventCard;
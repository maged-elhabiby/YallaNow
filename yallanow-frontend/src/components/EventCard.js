/*
EventCard.js
Event card component that displays the event image, title, location, date, time, and description.
*/
import React from 'react';
import { useNavigate } from 'react-router-dom';

const EventCard = ({ event, recommId }) => {
  const navigate = useNavigate();
  const formattedDate = event.eventStartTime.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
  const formattedTime = event.eventStartTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
  const formattedLocation = event.eventLocationCity + " " + event.eventLocationProvince + " " + event.eventLocationCountry


  const handleViewEvent = () => {
    navigate(`/event-details/${event.eventId}`, { state: { event, recommId } });
  };

  return (
    <div key={event.eventId} className="group relative">
      <div className="aspect-h-3 aspect-w-4 overflow-hidden rounded-lg bg-gray-100">
        <img src={event.eventImageUrl} alt="" className="object-cover object-center" />
        <div className="flex items-end p-4 opacity-0 group-hover:opacity-100" aria-hidden="true">
          <button onClick={handleViewEvent} className="w-full rounded-md bg-white bg-opacity-75 px-4 py-2 text-center text-sm font-medium text-gray-900 backdrop-blur backdrop-filter">
            View Event
          </button>
        </div>
      </div>
      <div className="mt-4 flex justify-between space-x-4 text-base font-medium text-gray-900">
        <div className="text-left">
          <p className="line-clamp-1">{event.eventTitle}</p>
          <p className="line-clamp-1">{formattedLocation}</p>
        </div>
        <div className="text-right">
          <p className="whitespace-nowrap">{formattedDate}</p>
          <p className="whitespace-nowrap">{formattedTime}</p>
        </div>
      </div>
      <p className="mt-2 text-sm text-gray-500 line-clamp-3">{event.eventDescription}</p>
    </div>
  );
}

export default EventCard;
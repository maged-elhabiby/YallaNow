import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import eventService from '../api/eventService';
import recombeeInteractions from '../api/recomebeeInteractions';

const EventDetailsPage = () => {
  const userId = 10001;
  const { state } = useLocation();
  const { event, recommId } = state;
  const [rsvpStatus, setRsvpStatus] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRsvpStatus = async () => {
      // Assuming you have access to the user's ID
      const status = await eventService.getUserRsvpStatusForEvent(userId, event.eventId);
      setRsvpStatus(status);
    };

    fetchRsvpStatus();
  }, [event.eventId]);

  const handleRsvpClick = async () => {
    if (rsvpStatus) {
      const success = await eventService.removeRsvpStatusFromEvent(userId, event.eventId);
      if (success) {
        setRsvpStatus(false);
        alert('Successfully un-RSVP\'d');
      } else {
        alert('Failed to un-RSVP');
      }
    } else {
      const success = await eventService.addRsvpStatusToEvent(userId, event.eventId);
      if (success) {
        setRsvpStatus(true);
        alert('Successfully RSVP\'d');
        // Send a purchase interaction to Recombee
        recombeeInteractions.addPurchaseInteraction(userId.toString(), event.eventId.toString(), recommId.toString());
      } else {
        alert('Failed to RSVP');
      }
    }
  };

  const handleVisitGroup = () => {
    // Navigate to the group page
    navigate(`/group/${event.groupId}`);
  };

  // Send recombee detail view when this page is loaded.
  useEffect(() => {
    console.log(`Sending detail view interaction for event ${event.eventId} with recommId ${recommId}`);
    recombeeInteractions.addDetailViewInteraction(userId, event.eventId, recommId);
  }, [event, recommId]);

  const formattedStartDate = event.eventStartTime.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
  const formattedStartTime = event.eventStartTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
  
  const formattedEndDate = event.eventEndTime.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
  const formattedEndTime = event.eventEndTime.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
  
  const formattedLocation = event.eventLocationCity + " " + event.eventLocationProvince + " " + event.eventLocationCountry;
  
  return (
    <div className="bg-white">
      <div className="mx-auto mt-32 px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
        {/* Product */}
        <div className="lg:grid lg:grid-cols-7 lg:grid-rows-1 lg:gap-x-8 lg:gap-y-10 xl:gap-x-16">
          {/* Product image */}
          <div className="lg:col-span-4 lg:row-end-1">
            <div className="aspect-h-3 aspect-w-4 overflow-hidden rounded-lg bg-gray-100">
              <img src={event.eventImageUrl} alt={event.eventImageAlt} className="object-cover object-center" />
            </div>
          </div>

          {/* Product details */}
          <div className="mx-auto mt-14 max-w-2xl sm:mt-16 lg:col-span-3 lg:row-span-2 lg:row-end-2 lg:mt-0 lg:max-w-none">
            <div className="flex flex-col-reverse">
              <div className="mt-4">
                <h1 className="text-2xl font-bold tracking-tight text-gray-900 sm:text-3xl">{event.eventTitle}</h1>
                <h2 id="information-heading" className="sr-only">Event information</h2>
                <p className="mt-2 text-sm text-gray-500">{formattedLocation}</p>
                <p className="mt-2 text-sm text-gray-500">From: {formattedStartDate} at {formattedStartTime}</p>
                <p className="mt-2 text-sm text-gray-500">To: {formattedEndDate} at {formattedEndTime}</p>
              </div>
            </div>
            <div className="mt-8">
              <h2 className="text-sm font-medium text-gray-900">Description</h2>
              <p className="prose prose-sm mt-4 text-gray-500">{event.eventDescription}</p>
            </div>

            <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-4 sm:grid-cols-2">
              <button
                onClick={handleRsvpClick}
                type="button"
                className="flex w-full items-center justify-center rounded-md border border-transparent bg-indigo-600 px-8 py-3 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 focus:ring-offset-gray-50"
              >
                 {rsvpStatus === 'Attending' ? 'Un-RSVP' : 'RSVP'}
              </button>
              <button
                onClick={handleVisitGroup}
                type="button"
                className="flex w-full items-center justify-center rounded-md border border-transparent bg-indigo-50 px-8 py-3 text-base font-medium text-indigo-700 hover:bg-indigo-100 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 focus:ring-offset-gray-50"
              >
                Visit Group
              </button>
            </div>

            
          </div>

          <div className="mx-auto mt-16 w-full max-w-2xl lg:col-span-4 lg:mt-0 lg:max-w-none">

          </div>
        </div>
      </div>
    </div>
  )
}

export default EventDetailsPage
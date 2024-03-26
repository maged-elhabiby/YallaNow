import React, { useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
// Import icons from Heroicons for visual elements in form inputs
import { CalendarIcon, LocationMarkerIcon, UsersIcon } from '@heroicons/react/solid';

const CreateEventPage = () => {
  const { groupId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const groupFromState = location.state?.groupId;
  const defaultImageUrl = "https://storage.googleapis.com/tmp-bucket-json-data/eventImage.png";

  const [formData, setFormData] = useState({
    eventTitle: '',
    eventDescription: '',
    eventLocationStreet: '',
    eventLocationCity: '',
    eventLocationProvince: '',
    eventLocationCountry: '',
    eventStartTime: '',
    eventEndTime: '',
    eventCapacity: '',
    eventImageUrl: '',
    groupId: groupId || groupFromState,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here, you would handle form submission, such as sending data to your backend or Firebase.
    const finalFormData = {
        ...formData,
        eventImageUrl: formData.eventImageUrl || defaultImageUrl,
      };  
    console.log(formData);
    navigate(-1); // Navigate back to the previous page or a specific route after form submission.
  };

  return (
    <div className="container mx-auto p-4">
        <div className=" px-2 py-12 sm:py-12 mt-8 lg:px-2">
            <div className="mx-auto max-w-2xl text-center">
                <p className="text-base font-semibold leading-7 text-indigo-600">Time to Create An Experience</p>
                <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">Create Your Event</h2>
                <p className="mt-6 text-lg leading-8 text-gray-600">
                Build you dream Event
                </p>
            </div>
        </div>
      <div className="max-w-3xl mx-auto bg-white rounded-lg shadow-md p-6">
        <h1 className="text-2xl font-bold mb-4">Create New Event</h1>
        <form onSubmit={handleSubmit}>

          {/* Event Title */}
          <div className="mb-4">
            <label htmlFor="eventTitle" className="block text-sm font-medium text-gray-700">Event Title</label>
            <input type="text" id="eventTitle" name="eventTitle" value={formData.eventTitle} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>

          {/* Event Description */}
          <div className="mb-4">
            <label htmlFor="eventDescription" className="block text-sm font-medium text-gray-700">Description</label>
            <textarea id="eventDescription" name="eventDescription" rows="4" value={formData.eventDescription} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"></textarea>
          </div>

          {/* Event Location */}
          <div className="mb-4">
            <label htmlFor="eventLocationStreet" className="block text-sm font-medium text-gray-700">Location</label>
            <input type="text" id="eventLocationStreet" name="eventLocationStreet" placeholder="Street Address" value={formData.eventLocationStreet} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
            <div className="flex gap-4 mt-2">
              <input type="text" id="eventLocationCity" name="eventLocationCity" placeholder="City" value={formData.eventLocationCity} onChange={handleChange} required className="flex-1 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
              <input type="text" id="eventLocationProvince" name="eventLocationProvince" placeholder="Province/State" value={formData.eventLocationProvince} onChange={handleChange} required className="flex-1 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
              <input type="text" id="eventLocationCountry" name="eventLocationCountry" placeholder="Country" value={formData.eventLocationCountry} onChange={handleChange} required className="flex-1 rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
            </div>
          </div>

          {/* Event Start and End Time */}
          <div className="flex gap-4 mb-4">
            <div className="flex-1">
              <label htmlFor="eventStartTime" className="block text-sm font-medium text-gray-700">Start Time
              </label>
              <input type="datetime-local" id="eventStartTime" name="eventStartTime" value={formData.eventStartTime} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
            </div>
            <div className="flex-1">
              <label htmlFor="eventEndTime" className="block text-sm font-medium text-gray-700">End Time</label>
              <input type="datetime-local" id="eventEndTime" name="eventEndTime" value={formData.eventEndTime} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
            </div>
          </div>

          {/* Event Capacity */}
          <div className="mb-4">
            <label htmlFor="eventCapacity" className="block text-sm font-medium text-gray-700">Capacity</label>
            <input type="number" id="eventCapacity" name="eventCapacity" placeholder="Number of attendees" value={formData.eventCapacity} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>

          {/* Event Image URL */}
          <div className="mb-4">
            <label htmlFor="eventImageUrl" className="block text-sm font-medium text-gray-700">Image URL</label>
            <input type="url" id="eventImageUrl" name="eventImageUrl" placeholder="http://example.com/image.jpg" value={formData.eventImageUrl} onChange={handleChange} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>

          {/* Submit Button */}
          <div className="flex justify-end">
            <button type="submit" className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
              Create Event
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateEventPage;

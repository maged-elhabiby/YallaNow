import React, { useState } from 'react';
import Nav from './nav.js';
import { PhotoIcon, UserCircleIcon } from '@heroicons/react/24/solid';

const CreateEventForm = () => {
  const [formData, setFormData] = useState({
    eventID: '',
    groupID: '',
    eventTitle: '',
    eventDescription: '',
    street: '',
    city: '',
    province: '',
    postalCode: '',
    country: '',
    eventStartTime: '',
    eventEndTime: '',
    status: 'Scheduled',
    capacity: '',
    imageUrl: ''
  });

  const handleSubmit = async (event) => {
    event.preventDefault();
    let imageId; // Declare variable to store the retrieved imageId
  
    try {
      // Fetch request to the image microservice to add the image
      const imageResponse = await fetch('http://localhost:8081/microservice/images/AddImage', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData.imageUrl),
        mode: 'cors',
        credentials: 'omit'
      });
  
      if (!imageResponse.ok) {
        throw new Error(`Error: ${imageResponse.status}`);
      }
  
      // Parse the response to get the imageId
      const imageResponseData = await imageResponse.json();
      console.log("Success adding image:", imageResponseData);
      imageId = imageResponseData.imageId; // Store the retrieved imageId
  
    } catch (error) {
      console.error("Failed to add image:", error);
      // Handle error, show error message to user, etc.
      return; // Exit function early if image addition fails
    }
  
    // Adjust formData to match the expected backend format
    const payload = {
      groupID: parseInt(formData.groupID),
      eventTitle: formData.eventTitle,
      eventDescription: formData.eventDescription,
      location: {
        street: formData.street,
        city: formData.city,
        province: formData.province,
        postalCode: formData.postalCode,
        country: formData.country        
      },
      eventStartTime: formData.eventStartTime,
      eventEndTime: formData.eventEndTime,
      status: formData.status,
      capacity: parseInt(formData.capacity),
      imageID: imageId // Use the retrieved imageId in the payload
    };
  
    try {
      // Fetch request to add the event using the adjusted payload
      const eventResponse = await fetch('http://localhost:8082/microservice/events/AddEvent', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
        mode: 'cors',
        credentials: 'omit'
      });
  
      if (!eventResponse.ok) {
        throw new Error(`Error: ${eventResponse.status}`);
      }
  
      // Parse the response for the event creation request
      const eventResponseData = await eventResponse.json();
      console.log("Success adding event:", eventResponseData);
      // Handle successful form submission here, e.g., showing a success message or redirecting the user
  
    } catch (error) {
      console.error("Failed to submit form:", error);
      // Handle errors here, e.g., showing an error message to the user
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  return (
    <body>
      {Nav()}
      <div className="max-w-4xl mx-auto my-10 p-5 bg-white rounded-lg shadow">
        <form onSubmit={handleSubmit} className="space-y-8 divide-y divide-gray-200">
          <div className="space-y-8 divide-y divide-gray-200">
            <div>
              <h3 className="text-lg leading-6 font-medium text-gray-900">Create New Event</h3>
              <p className="mt-1 text-sm text-gray-500">
                Enter the details of the event you're planning.
              </p>
            </div>
            <div className="mt-6 grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
              {/* Event Title */}
              <div className="sm:col-span-6">
                <label htmlFor="eventTitle" className="block text-sm font-medium text-gray-700">
                  Event Title
                </label>
                <input
                  type="text"
                  name="eventTitle"
                  id="eventTitle"
                  required
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.eventTitle}
                  onChange={handleChange}
                />
              </div>

              {/* Event Description */}
              <div className="sm:col-span-6">
                <label htmlFor="eventDescription" className="block text-sm font-medium text-gray-700">
                  Description
                </label>
                <textarea
                  id="eventDescription"
                  name="eventDescription"
                  rows="3"
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.eventDescription}
                  onChange={handleChange}
                />
              </div>

              {/* Street */}
              <div className="sm:col-span-6">
                <label htmlFor="street" className="block text-sm font-medium text-gray-700">
                  Street
                </label>
                <input
                  type="text"
                  name="street"
                  id="street"
                  required
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.street}
                  onChange={handleChange}
                />
              </div>

              {/* City */}
              <div className="sm:col-span-3">
                <label htmlFor="city" className="block text-sm font-medium text-gray-700">
                  City
                </label>
                <input
                  type="text"
                  name="city"
                  id="city"
                  required
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.city}
                  onChange={handleChange}
                />
              </div>

              {/* Province */}
              <div className="sm:col-span-3">
                <label htmlFor="province" className="block text-sm font-medium text-gray-700">
                  Province
                </label>
                <input
                  type="text"
                  name="province"
                  id="province"
                  required
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.province}
                  onChange={handleChange}
                />
              </div>

              {/* PostalCode */}
              <div className="sm:col-span-3">
                <label htmlFor="postalCode" className="block text-sm font-medium text-gray-700">
                  Postal Code
                </label>
                <input
                  type="text"
                  name="postalCode"
                  id="postalCode"
                  required
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                  value={formData.postalCode}
                  onChange={handleChange}
                />
              </div>

              {/* Country */}
              <div className="sm:col-span-3">
                  <label htmlFor="country" className="block text-sm font-medium text-gray-700">
                      Country
                  </label>
                  <input
                      type="text"
                      name="country"
                      id="country"
                      autoComplete="country-name"
                      required
                      className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.country}
                      onChange={handleChange}
                  />
              </div>

              {/* Event Start Time */}
              <div className="sm:col-span-3">
                  <label htmlFor="eventStartTime" className="block text-sm font-medium text-gray-700">
                      Start Time
                  </label>
                  <input
                      type="datetime-local"
                      name="eventStartTime"
                      id="eventStartTime"
                      required
                      className="mt-1 block w-full rounded-md border-gray-800 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.eventStartTime}
                      onChange={handleChange}
                  />
              </div>

              {/* Event End Time */}
              <div className="sm:col-span-3">
                  <label htmlFor="eventEndTime" className="block text-sm font-medium text-gray-700">
                      End Time
                  </label>
                  <input
                      type="datetime-local"
                      name="eventEndTime"
                      id="eventEndTime"
                      required
                      className="mt-1 block w-full rounded-md border-gray-800 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.eventEndTime}
                      onChange={handleChange}
                  />
              </div>

              {/* Status */}
              <div className="sm:col-span-3">
                  <label htmlFor="status" className="block text-sm font-medium text-gray-700">
                      Status
                  </label>
                  <select
                      id="status"
                      name="status"
                      autoComplete="status"
                      className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.status || 'Scheduled'}
                      onChange={handleChange}
                  >
                      <option value="Scheduled">Scheduled</option>
                      <option value="Completed">Completed</option>
                      <option value="Cancelled">Cancelled</option>
                  </select>
              </div>

              {/* Capacity */}
              <div className="sm:col-span-3">
                  <label htmlFor="capacity" className="block text-sm font-medium text-gray-700">
                      Capacity
                  </label>
                  <input
                      type="number"
                      name="capacity"
                      id="capacity"
                      required
                      pattern="[0-9]*"
                      title="Please enter numbers only." 
                      className="mt-1 block w-full rounded-md border-gray-700 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.capacity}
                      onChange={handleChange}
                  />
              </div>

              {/* Image URL */}
              <div className="sm:col-span-6">
                  <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">
                      Image URL
                  </label>
                  <input
                      type="url"
                      name="imageUrl"
                      id="imageUrl"
                      required
                      className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                      value={formData.imageUrl}
                      onChange={handleChange}
                  />
              </div>
            </div>
        </div>

        <div className="pt-5">
            <div className="flex justify-end">
                <button
                    type="button"
                    className="rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                    onClick={() => setFormData({ ...formData })} // Reset the form or handle cancellation logic here
                >
                    Cancel
                </button>
                <button
                    type="submit"
                    className="ml-3 inline-flex justify-center rounded-md border border-transparent bg-[#3788d8] py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                >
                    Save
                </button>
            </div>
        </div>
      </form>
      </div>
    </body>
  );
};

export default CreateEventForm;

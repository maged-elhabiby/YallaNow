import React, { useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
// Import icons from Heroicons for visual elements in form inputs
import { CalendarIcon, LocationMarkerIcon, UsersIcon } from '@heroicons/react/solid';
import { PhotoIcon } from '@heroicons/react/24/solid'


const CreateEventPage = () => {
  const { groupId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const groupFromState = location.state?.groupId;

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
    groupId: groupId || groupFromState,
  });

  const [imageBase64, setImageBase64] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageBase64(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Here, you would handle form submission, such as sending data to your backend or Firebase.
    const finalFormData = {
      ...formData,
      eventImageBase64: imageBase64,
    };

    try {
      // Send the data to your backend
      console.log(finalFormData);
      console.log('Event created successfully');
       // Navigate back to the previous page or a specific route after form submission.
    } catch (error) {
      console.error('Error creating event:', error);
    }
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

        <div className="grid max-w-2xl grid-cols-1 gap-x-6 gap-y-4 sm:grid-cols-6 md:col-span-2">
          {/* Event Title */}
          <div className="sm:col-span-3">
            <label htmlFor="eventTitle" className="block text-sm font-medium text-gray-700">Event Title</label>
            <input type="text" id="eventTitle" name="eventTitle" value={formData.eventTitle} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>

          {/* Event Description */}
          <div className="sm:col-span-4">
            <label htmlFor="eventDescription" className="block text-sm font-medium text-gray-700">Description</label>
            <textarea id="eventDescription" name="eventDescription" rows="4" value={formData.eventDescription} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"></textarea>
          </div>

          {/* Event Location */}
          <div className="sm:col-span-3">
              <label htmlFor="country" className="block text-sm font-medium leading-6 text-gray-900">
                Country
              </label>
              <div className="mt-2">
                <select
                  value={formData.eventLocationCountry} onChange={handleChange} required
                  id="country"
                  name="country"
                  autoComplete="country-name"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                >
                  <option>United States</option>
                  <option>Canada</option>
                  <option>Mexico</option>
                </select>
              </div>
            </div>

            <div className="col-span-full">
              <label htmlFor="street-address" className="block text-sm font-medium leading-6 text-gray-900">
                Street address
              </label>
              <div className="mt-2">
                <input
                value={formData.eventLocationStreet} onChange={handleChange} required
                  type="text"
                  name="street-address"
                  id="street-address"
                  autoComplete="street-address"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div className="sm:col-span-2 sm:col-start-1">
              <label htmlFor="city" className="block text-sm font-medium leading-6 text-gray-900">
                City
              </label>
              <div className="mt-2">
                <input
                value={formData.eventLocationCity} onChange={handleChange} required 
                  type="text"
                  name="city"
                  id="city"
                  autoComplete="address-level2"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div className="sm:col-span-2">
              <label htmlFor="region" className="block text-sm font-medium leading-6 text-gray-900">
                State / Province
              </label>
              <div className="mt-2">
                <input
                value={formData.eventLocationProvince} onChange={handleChange} required
                  type="text"
                  name="region"
                  id="region"
                  autoComplete="address-level1"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div className="sm:col-span-2">
              <label htmlFor="postal-code" className="block text-sm font-medium leading-6 text-gray-900">
                ZIP / Postal code
              </label>
              <div className="mt-2">
                <input
                  type="text"
                  name="postal-code"
                  id="postal-code"
                  autoComplete="postal-code"
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>
          </div>

          {/* Event Start and End Time */}
          <div className="mt-4 grid grid-cols-2 gap-x-8 gap-y-4 md:grid-cols-3">
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
          <div className="mt-4">
            <label htmlFor="eventCapacity" className="block text-sm font-medium text-gray-700">Capacity</label>
            <input type="number" id="eventCapacity" name="eventCapacity" placeholder="Number of attendees" value={formData.eventCapacity} onChange={handleChange} required className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm" />
          </div>

          {/* Event Image URL */}


          <div className="mt-4 col-span-full">
              <label htmlFor="cover-photo" className="block text-sm font-medium leading-6 text-gray-900">
                Cover photo
              </label>
              <div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                <div className="text-center">
                  <PhotoIcon className="mx-auto h-12 w-12 text-gray-300" aria-hidden="true" />
                  <div className="mt-4 flex text-sm leading-6 text-gray-600">
                    <label
                      htmlFor="file-upload"
                      className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                    >
                      <span>Upload an image</span>
                      <input type="file" id="file-uploda" name="file-upload" accept="image/*" onChange={handleImageChange} className="sr-only" />
                    </label>
                    <p className="pl-1">or drag and drop</p>
                  </div>
                  <p className="text-xs leading-5 text-gray-600">PNG, JPG, GIF up to 10MB</p>
                </div>
              </div>
            </div>

          {/* Submit Button */}
          <div className="mt-4 flex justify-end ">
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

import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import eventData from './exampleResponse.json';
import { Dropdown, Carousel } from 'flowbite-react';

function MainPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('');
  const navigate = useNavigate();

  // Function to sort events based on the selected sort option
  const sortEvents = (events) => {
    if (sortBy === 'name') {
      return events.sort((a, b) => a.eventName.localeCompare(b.eventName));
    } else if (sortBy === 'location') {
      return events.sort((a, b) => a.location.localeCompare(b.location));
    } else if (sortBy === 'group') {
      return events.sort((a, b) => a.group.localeCompare(b.group));
    } else {
      return events.sort((a, b) => {
        const dateA = new Date(`${a.eventDate} ${a.eventTime}`);
        const dateB = new Date(`${b.eventDate} ${b.eventTime}`);
        return dateA - dateB;
      });
    }
  };

  const filteredEvents = sortEvents(
    eventData.events.filter((event) => {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      const lowerCaseEventName = event.eventName.toLowerCase();
      const lowerCaseLocation = event.location.toLowerCase();
      const lowerCaseGroup = event.group.toLowerCase();
      const eventDate = new Date(event.date);
  
      switch (sortBy) {
        case "name":
          return lowerCaseEventName.includes(lowerCaseSearchTerm);
        case "location":
          return lowerCaseLocation.includes(lowerCaseSearchTerm);
        case "group":
          return lowerCaseGroup.includes(lowerCaseSearchTerm);
        case "date":
          return eventDate.toLocaleDateString().includes(lowerCaseSearchTerm);
        default:
          return true; 
      }
    })
  );

  return (
    <body class ="bg-gray-100 ">
      <nav class="flex justify-between items-center bg-white py-8 px-6 mb-4 border-b border-gray-300">
        <div class="flex items-center space-x-4">
          <Dropdown label="Dropdown button" dismissOnClick={false} class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded">
            <Dropdown.Item>My Events</Dropdown.Item>
            <Dropdown.Item>Chat</Dropdown.Item>
            <Dropdown.Item>My Events</Dropdown.Item>
          </Dropdown>
        </div>
        <div class="flex items-center">
          <p class="font-serif font-bold text-5xl italic text-blue-300 mr-40" onClick = {() => navigate('/')}>YallaNow</p>
        </div>
        <div>
          <button class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded" onClick = {() => navigate('/Login')}>
            Login
          </button>
        </div>
      </nav>

      <div class="items-center justify-center flex mb-4 ">
        <div class="h-56 sm:h-64 xl:h-80 2xl:h-96 w-2/5">
          <Carousel slideInterval={5000} pauseOnHover>
            <img src="https://flowbite.com/docs/images/carousel/carousel-1.svg" alt="..." />
            <img src="https://flowbite.com/docs/images/carousel/carousel-2.svg" alt="..." />
            <img src="https://flowbite.com/docs/images/carousel/carousel-3.svg" alt="..." />
            <img src="https://flowbite.com/docs/images/carousel/carousel-4.svg" alt="..." />
            <img src="https://flowbite.com/docs/images/carousel/carousel-5.svg" alt="..." />
          </Carousel>
        </div>
      </div>

      <div class = "flex items-center mb-4 justify-center">
        <input
          class="px-4 py-2 border rounded-md w-2/5"
          type="text"
          placeholder="Search Events"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <div class="flex items-center">
          <select value={sortBy} class="px-4 py-2 border rounded-md" onChange={(e) => setSortBy(e.target.value)}>
            <option value="date">Date</option>
            <option value="name">Name</option>
            <option value="location">Location</option>
            <option value="group">Group</option>
          </select>
        </div>
      </div>
      <div className="event-list" class = "flex flex-wrap ">
        {filteredEvents.map((event) => (
          <div key={event.eventID} className="event-card" class = "bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1" 
          
            onClick = {() => navigate("event/",{ state: { event: event} })}>
            <strong>{event.eventName}</strong>
            <p>Group: {event.group}</p>
            <p>Date: {event.eventDate}</p>
            <p>Time: {event.eventTime}</p>
            <p>Location: {event.location}</p>
          </div>
        ))}
      </div>
    </body>
  );
}

export default MainPage;
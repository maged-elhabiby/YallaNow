import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import eventData from './exampleResponse.json';
import groupData from './exampleGroups.json';
import { Carousel, Button  } from 'flowbite-react';
import Nav from './nav.js';

function MainPage() {
  const [displayType, setDisplayType] = useState('events');
  const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('');
  const navigate = useNavigate();
  const modifiedEventData = stringifyEventData(eventData);
  const modifiedGroupData = stringifyGroupData(groupData);

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

  function stringifyEventData(data) {
    const eventsArray = data.events; // Renamed the variable to eventsArray
    return eventsArray.map(event => {
      const { location, eventStartTime, eventEndTime, ...rest } = event;
      const stringifiedLocation = JSON.stringify(location);
      const stringifiedStartTime = JSON.stringify(eventStartTime);
      const stringifiedEndTime = JSON.stringify(eventEndTime);
      return {
        ...rest,
        location: stringifiedLocation,
        eventStartTime: stringifiedStartTime,
        eventEndTime: stringifiedEndTime
      };
    });
  }

  const filteredEvents = sortEvents(
    modifiedEventData.filter((event) => {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      const lowerCaseEventName = (event.eventTitle).toLowerCase();
      const lowerCaseLocation = JSON.parse(event.location).city.toLowerCase();
  
      switch (sortBy) {
        case "name":
          return lowerCaseEventName.includes(lowerCaseSearchTerm);
        case "location":
          return lowerCaseLocation.includes(lowerCaseSearchTerm);
        default:
          return true;
      }
    })
  );

  // Group functions
  //sort groups based on name
  function sortGroups(groups) {
    return groups.sort((a, b) => a.groupName.localeCompare(b.groupName));
  }


  function stringifyGroupData(data) {
    const groupsArray = data.groups; // Renamed the variable to groupsArray
    return groupsArray.map(group => {
      const { events, ...rest } = group;
      const stringifiedGroupEvents = JSON.stringify(events);
      return {
        ...rest,
        location: stringifiedGroupEvents
      };
    });
  }

  const filteredGroups = sortGroups(
    modifiedGroupData.filter((group) => {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      const lowerCaseGroupName = (group.groupName).toLowerCase();
  
      switch (sortBy) {
        case "name":
          return lowerCaseGroupName.includes(lowerCaseSearchTerm);
        default:
          return true;
      }
    })
  );

  return (
    <body class ="bg-gray-100 ">
      {Nav()}

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

      <div className="flex justify-center items-center mb-2">
        <Button.Group>
          <Button color={displayType === 'events' ? 'blue' : 'gray'} onClick={() => setDisplayType('events')}>Events</Button>
          <Button color={displayType === 'groups' ? 'blue' : 'gray'} onClick={() => setDisplayType('groups')}>Groups</Button>
        </Button.Group>
      </div>

      <div className="flex items-center mb-4 justify-center">
        <input
          className="px-4 py-2 border rounded-md w-2/5"
          type="text"
          placeholder={`Search ${displayType === 'events' ? 'Events' : 'Groups'}`}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      {displayType === 'events' && (
        <div className="flex items-center">
          <select value={sortBy} className="px-4 py-2 border rounded-md" onChange={(e) => setSortBy(e.target.value)}>
            <option value="name">Name</option>
            <option value="location">Location</option>
          </select>
        </div>
      )}
      </div>

      <div className="content-list flex flex-wrap">
        {displayType === 'events' ? (
          filteredEvents.map((event) => (
            <div key={event.eventID} className="event-card bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1" 
              onClick={() => navigate(`event/${event.eventID}`, { state: { event: event } })}>
              <strong>{event.eventTitle}</strong>
              <p>Group: {event.group}</p>
              <p>Date: {event.eventDate}</p>
              <p>Time: {event.eventTime}</p>
              <p>Location: {event.location}</p>
            </div>
          ))
        ) : (
          filteredGroups.map((group) => (
            <div key={group.groupID} className="group-card bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1"
            onClick={() => navigate(`group/${group.groupID}`, { state: { group: group } })}>
              <strong>Group ID : {group.groupName}</strong>
            </div>
          ))
        )}
      </div>
    </body>
  );
}

export default MainPage;
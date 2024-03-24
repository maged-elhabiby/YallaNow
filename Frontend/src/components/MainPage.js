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
  const [searchTermLocation, setSearchTermLocation] = useState('');
  const [searchTermEvent, setSearchTermEvent] = useState('');
  const [searchTermGroup, setSearchTermGroup] = useState('');
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
      const lowerCaseSearchTermLocation = searchTermLocation.toLowerCase();
      const lowerCaseSearchTermEvent = searchTermEvent.toLowerCase();
      const lowerCaseEventName = (event.eventTitle).toLowerCase();
      const lowerCaseLocation = JSON.parse(event.location).city.toLowerCase() + JSON.parse(event.location).street.toString() + JSON.parse(event.location).province.toLowerCase() + JSON.parse(event.location).country.toLowerCase();
  
      if (lowerCaseSearchTermLocation === '' && lowerCaseSearchTermEvent === '') {
        return true;
      } else if (lowerCaseSearchTermLocation === '') {
        return lowerCaseEventName.includes(lowerCaseSearchTermEvent);
      } else if (lowerCaseSearchTermEvent === '') {
        return lowerCaseLocation.includes(lowerCaseSearchTermLocation);
      } else {
        return lowerCaseEventName.includes(lowerCaseSearchTermEvent) && lowerCaseLocation.includes(lowerCaseSearchTermLocation);
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
        events: stringifiedGroupEvents
      };
    });
  }

  const filteredGroups = sortGroups(
    modifiedGroupData.filter((group) => {
      const lowerCaseSearchTerm = searchTermGroup.toLowerCase();
      const lowerCaseGroupName = (group.groupName).toLowerCase();
  
      if (lowerCaseSearchTerm === '') {
        return true;
      } else {
        return lowerCaseGroupName.includes(lowerCaseSearchTerm);
      }
    })
  );

  const nextPage = () => {
    setCurrentPage((prevPage) => prevPage + 1);
  };

  const prevPage = () => {
    if (currentPage > 1) {
      setCurrentPage((prevPage) => prevPage - 1);
    }
  };

  const itemsPerPage = 9;
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const currentItems = displayType === 'events' ? filteredEvents.slice(indexOfFirstItem, indexOfLastItem) : filteredGroups.slice(indexOfFirstItem, indexOfLastItem);

  return (
    <body class ="bg-gray-100 min-h-screen">
      {Nav()}

      <div class="items-center justify-center flex my-4 ">
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
          <Button color={displayType === 'events' ? 'blue' : 'gray'} onClick={() => {setDisplayType('events'); setCurrentPage(1);}}>Events</Button>
          <Button color={displayType === 'groups' ? 'blue' : 'gray'} onClick={() => {setDisplayType('groups'); setCurrentPage(1);}}>Groups</Button>
        </Button.Group>
      </div>

      <div className="flex items-center mb-4 justify-center">
      {displayType !== 'events' && (
        <input
        className="px-4 py-2 border rounded-md w-2/5"
        type="text"
        placeholder={`Search ${displayType === 'events' ? 'Location' : 'Groups'}`}
        value={searchTermGroup}
        onChange={(e) => setSearchTermGroup(e.target.value)}
      />
      )}


      {displayType === 'events' && (
        <input
        className="px-4 py-2 border divide-x-1 rounded-l-md w-1/3"
        type="text"
        placeholder={`Search ${displayType === 'events' ? 'Events' : 'Groups'}`}
        value={searchTermEvent}
        onChange={(e) => setSearchTermEvent(e.target.value)}
      />
      )}

      {displayType === 'events' && (
        <input
        className="px-4 py-2 border rounded-r-md w-1/3"
        type="text"
        placeholder={`Search ${displayType === 'events' ? 'Locations' : 'Groups'}`}
        value={searchTermLocation}
        onChange={(e) => setSearchTermLocation(e.target.value)}
      />
      )}

      </div>

      <div className="content-list flex flex-wrap overflow-y-auto bg-gray-300 mx-10 rounded-2xl" style={{ maxHeight: '500px' }}>
        {currentItems.map((item) => (
          <div key={displayType === 'events' ? item.eventID : item.groupID} className={displayType === 'events' ? "event-card bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1" : "group-card bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1"} 
            onClick={() => navigate(`/${displayType}/${displayType === 'events' ? item.eventID : item.groupID}`, { state: { [displayType === 'events' ? 'event' : 'group']: item } })}>
            <strong>{displayType === 'events' ? item.eventTitle : `Group : ${item.groupName}`}</strong>
            {displayType === 'events' && (
              <>
                <p>Group: {item.group}</p>
                <p>Date: {item.eventDate}</p>
                <p>Time: {item.eventTime}</p>
                <p>Location: {item.location}</p>
              </>
            )}
          </div>    
        ))}
      </div>
      <div className="flex justify-center items-center w-full my-2">
        <button onClick={prevPage} disabled={currentPage === 1} className="block" class="text-white bg-blue-600 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">{"<"} Previous Page</button>
        <div>
          <p className="block"  class="text-white bg-gray-600 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">{currentPage}</p>
        </div>
        <button onClick={nextPage} disabled={indexOfLastItem >= (displayType === 'events' ? filteredEvents.length : filteredGroups.length)} className="block" class="text-white bg-blue-600 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Next Page {">"}</button>
      </div>
    </body>
  );
}

export default MainPage;
import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import eventData from './exampleResponse.json';

function MainPage() {
    const [searchTerm, setSearchTerm] = useState('');
  const [sortBy, setSortBy] = useState('');

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

  // Filter and sort events based on search term and sort option
  const filteredEvents = sortEvents(
    eventData.events.filter((event) =>
      event.eventName.toLowerCase().includes(searchTerm.toLowerCase())
    )
  );

  return (
    <div>
        <div className="nav">
            <Link to="/Login">Login</Link>
        </div>

      <h1>Event List</h1>
      <div>
        <input
          type="text"
          placeholder="Search by event name"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
          <option value="">Sort by</option>
          <option value="name">Name</option>
          <option value="location">Location</option>
          <option value="group">Group</option>
          <option value="date">Date and Time</option>
        </select>
      </div>
      <div className="event-list">
        {filteredEvents.map((event) => (
          <div key={event.eventID} className="event-card">
            <strong>{event.eventName}</strong>
            <p>Group: {event.group}</p>
            <p>Date: {event.eventDate}</p>
            <p>Time: {event.eventTime}</p>
            <p>Location: {event.location}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MainPage;
// src/components/MyCalendar.js
import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';

const MyCalendar = ({ events }) => { // Accept events as a prop
  return (
    <FullCalendar
      plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
      initialView="dayGridMonth"
      headerToolbar={{
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      }}
      events={events} // Use the events prop
      eventClick={(eventClickInfo) => {
        // Implement custom behavior when an event is clicked.
        // For example, you might want to open a modal with event details.
        alert(`Event: ${eventClickInfo.event.title}`);
      }}
    />
  );
};

export default MyCalendar;

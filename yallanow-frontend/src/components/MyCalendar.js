/*
MyCalendar.js
MyCalendar component that displays a calendar with events.
*/
import React, {useEffect, useRef, useState} from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import listPlugin from '@fullcalendar/list';
import CalendarHeader from './CalendarHeader';
import '../index.css';

const MyCalendar = ({events}) => {
    const calendarRef = useRef(null);
    const [title, setTitle] = useState('');

    useEffect(() => {
        const updateTitle = () => {
            if (calendarRef.current) {
                const calendarApi = calendarRef.current.getApi();
                setTitle(calendarApi.view.title);
            }
        };
        if (calendarRef.current) {
            updateTitle();
        }
    }, []);

    const handleViewChange = (view) => {
        if (calendarRef.current) {
            const calendarApi = calendarRef.current.getApi();
            calendarApi.changeView(view);
            setTitle(calendarApi.view.title);
        }
    };

    const navigate = (action) => {
        if (calendarRef.current) {
            const calendarApi = calendarRef.current.getApi();
            if (typeof calendarApi[action] === 'function') {
                calendarApi[action]();
                setTitle(calendarApi.view.title);
            } else {
                console.error(`The method ${action} does not exist on the calendarApi object.`);
            }
        }
    };


    return (
        <div className="calendar-container">
            <CalendarHeader
                onPrev={() => navigate('prev')}
                onNext={() => navigate('next')}
                onToday={() => navigate('today')}
                title={title}
                onViewChange={handleViewChange}
            />
            <FullCalendar
                ref={calendarRef}
                plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin, listPlugin]}
                initialView="dayGridMonth"
                headerToolbar={false}
                events={events}
                selectable={true}
                selectMirror={true}
                dayMaxEvents={true}
                datesSet={() => {
                    if (calendarRef.current) {
                        setTitle(calendarRef.current.getApi().view.title);
                    }
                }}
            />
        </div>
    );
};

export default MyCalendar;

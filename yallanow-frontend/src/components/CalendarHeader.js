// CalendarHeader.js
import React from 'react';
import '../index.css';

const CalendarHeader = ({ onPrev, onNext, onToday, title, onViewChange }) => {
    return (
        <div className="calendar-header-container flex flex-col items-center mb-3"> {/* Increase bottom margin */}
            <div className="mb-1"> {/* Adjust this value as needed to add space above the title */}
                <span className="text-2xl font-semibold">{title}</span>
            </div>
            <div className="flex justify-between w-full mt-1"> {/* Add space above buttons */}
                <div>
                    <button onClick={onPrev} className="calendar-header-button">Prev</button>
                    <button onClick={onToday} className="calendar-header-button today-button">Today</button>
                    <button onClick={onNext} className="calendar-header-button">Next</button>
                </div>
                <div>
                    <button onClick={() => onViewChange('dayGridMonth')} className="calendar-header-button">Month</button>
                    <button onClick={() => onViewChange('timeGridWeek')} className="calendar-header-button">Week</button>
                    <button onClick={() => onViewChange('timeGridDay')} className="calendar-header-button">Day</button>
                    <button onClick={() => onViewChange('listWeek')} className="calendar-header-button">List</button>
                </div>
            </div>
        </div>
    );
};

export default CalendarHeader;

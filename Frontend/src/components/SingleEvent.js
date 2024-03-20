import React, {useState} from 'react';
import {Link, useNavigate, useParams, useLocation} from 'react-router-dom';
import { Dropdown} from 'flowbite-react';
import '../App.css';

function SingleEvent() {
    const location = useLocation();
    const { event } = location.state || {};
    const navigate = useNavigate();

    if (!event) {
        return (
            <body>
                <nav class="flex justify-between items-center bg-white py-8 px-6 mb-4 border-b border-gray-300">
                    <div class="flex items-center space-x-4">
                    <Dropdown label="Dropdown button" dismissOnClick={false} class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded">
                        <Dropdown.Item>My Events</Dropdown.Item>
                        <Dropdown.Item>Chat</Dropdown.Item>
                        <Dropdown.Item>My Events</Dropdown.Item>
                    </Dropdown>
                    </div>
                    <div class="flex items-center">
                    <p class="test-blue font-serif font-bold text-5xl italic text-blue-300 mr-40"  onClick = {() => navigate('/')}>YallaNow</p>
                    </div>
                    <div>
                    <button class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded" onClick = {() => navigate('/Login')}>
                        Login
                    </button>
                    </div>
                </nav>
                <div class="flex justify-center items-center pt-20">
                    <p class="test-blue font-serif font-bold text-5xl italic text-gray-500 mr-40" >Error 404 - Event Not Found</p>
                </div>
            </body>
        );
    }

    return (
        <body>
            <nav class="flex justify-between items-center bg-white py-8 px-6 mb-4 border-b border-gray-300">
                <div class="flex items-center space-x-4">
                <Dropdown label="Dropdown button" dismissOnClick={false} class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded">
                    <Dropdown.Item>My Events</Dropdown.Item>
                    <Dropdown.Item>Chat</Dropdown.Item>
                    <Dropdown.Item>My Events</Dropdown.Item>
                </Dropdown>
                </div>
                <div class="flex items-center">
                <p class="test-blue font-serif font-bold text-5xl italic text-blue-300 mr-40"  onClick = {() => navigate('/')}>YallaNow</p>
                </div>
                <div>
                <button class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded" onClick = {() => navigate('/Login')}>
                    Login
                </button>
                </div>
            </nav>
            <div className="singleEvent">
                <h1>SingleEvent: {event.eventName}</h1>
                <p>Welcome to the single event page</p>
            </div>
        </body>
    );
}

export default SingleEvent;
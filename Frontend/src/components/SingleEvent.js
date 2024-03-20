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
            <body class ="bg-gray-100 h-screen">
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
        <body class ="bg-gray-100 h-screen">
            <nav class="flex justify-between items-center bg-white py-8 px-6 mb-4 border-b border-gray-300">
                <div class="flex items-center space-x-4">
                <Dropdown label="Dropdown button" dismissOnClick={false} class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded">
                    <Dropdown.Item>My Events</Dropdown.Item>
                    <Dropdown.Item>Chat</Dropdown.Item>
                    <Dropdown.Item>My Events</Dropdown.Item>
                </Dropdown>
                </div>
                <div class="flex items-center">
                <p class="font-serif font-bold text-5xl italic text-blue-300 mr-40"  onClick = {() => navigate('/')}>YallaNow</p>
                </div>
                <div>
                <button class="bg-blue-300 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded" onClick = {() => navigate('/Login')}>
                    Login
                </button>
                </div>
            </nav>

            <div className="singleEvent" class="flex items-center justify-center">
                <div class="bg-white mx-auto rounded-xl shadow-lg items-center w-3/5 p-6 mt-4">
                    <p class="font-serif font-bold text-5xl italic text-center pb-1 block">{event.eventName}</p>
                    <hr class="w-full my-4"></hr>
                    <div class="flex">
                        <img src="https://flowbite.com/docs/images/carousel/carousel-1.svg" alt="..." class = "block mx-auto max-w-2xl max-h-72 min-h-44 min-w-72"/>
                    </div>
                    <hr class="w-full my-4"></hr>
                    <div class="flex justify-between">
                        <p class="block text-2xl">Event Location:</p>
                        <p class="block text-2xl"> Event Date &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Event Time</p>
                    </div>
                    <div class="flex justify-between">
                        <p class="block text-3xl font-bold">{event.location}</p>
                        <p class="block text-3xl font-bold"> {event.eventDate} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;{event.eventTime}</p>
                    </div>
                    <hr class="w-full my-4"></hr>
                </div>
            </div>
        </body>
    );
}

export default SingleEvent;
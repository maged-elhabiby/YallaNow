import React, {useState} from 'react';
import {Link, useNavigate, useParams, useLocation} from 'react-router-dom';
import { Dropdown} from 'flowbite-react';
import '../App.css';
import Nav from './nav.js';

function SingleEvent() {
    const location = useLocation();
    const { event } = location.state || {};
    const navigate = useNavigate();

    if (!event) {
        return (
            <body class ="bg-gray-100 h-screen">
                {Nav()}
                <div class="flex justify-center items-center pt-20">
                    <p class="test-blue font-serif font-bold text-5xl italic text-gray-500 mr-40" >Error 404 - Event Not Found</p>
                </div>
            </body>
        );
    }

    return (
        <body class ="bg-gray-100 h-screen">
            {Nav()}

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
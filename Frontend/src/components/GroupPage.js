import React, {useState} from 'react';
import {Link, useNavigate, useParams, useLocation} from 'react-router-dom';
import '../App.css';
import '../output.css';
import { Carousel, Button  } from 'flowbite-react';
import Nav from './nav.js';

function GroupPage() {
    const location = useLocation();
    const { group } = location.state || {};
    const navigate = useNavigate();
    const groupEvents = getEvents();


    //grap all of the events from the group object and parses them from the string
    function getEvents(){
        return JSON.parse(group.events);
    }


    //if the group is not found, return an error message
    if (!group) {   
        return (
            <body class ="bg-gray-100 h-screen">
                <Nav />
                <div class="flex justify-center items-center pt-20">
                    <p class="test-blue font-serif font-bold text-5xl italic text-gray-500 mr-40" >Error 404 - Group Not Found</p>
                </div>
            </body>
        );
    }

    //return the group information
    //a button to join the group is also included
    //the group's events are also displayed
    //each event is a clickable card that will take the user to the event page
    return (
        <body  class ="bg-gray-100 min-h-screen">
            <Nav />
            <div className="groupPage" class="flex">

                <div class="w-1/3 pl-5">
                    <div class="bg-white mx-auto rounded-xl shadow-lg float-left p-6 mt-4 justify-left">
                        <p class="font-serif font-bold text-5xl italic text-center pb-1 block">{group.groupName}</p>
                        <hr class="w-full my-4"></hr>

                        <div class="flex justify-between items-center">
                            <p class="block text font-bold text-2xl">Members: {group.members} </p>
                            <button class="bg-blue-300 hover:bg-blue-400 text-white font-semibold py-2.5 px-4 rounded">Join Group</button>
                        </div>
                    </div>
                </div>

                <div class="w-2/3 px-5 pr-[20%] ">
                    <div class="block font-bold text-4xl">
                        <p class="text-center text-blue-400">Events</p>
                    </div>
                    <div class="flex flex-wrap">
                        <div class="mx-auto">
                            {groupEvents.map((event) => (
                                <div key={event.eventID} className="event-card bg-white mx-auto rounded-xl shadow-lg items-center space-x-4 max-w-sm p-6 mt-4 m-1" 
                                    onClick={() => navigate(`/event/${event.eventID}`, { state: { event: event } })}>
                                    <strong>{event.eventTitle}</strong>
                                </div>
                            ))
                            }   
                        </div>
                    </div>

                </div>


            </div>
        </body>
    );
}

export default GroupPage;
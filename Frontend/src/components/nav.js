import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import { Dropdown } from 'flowbite-react';

function Nav(){
    const navigate = useNavigate();

    return (      
    <nav class="flex justify-between items-center bg-white py-4 px-4 mb-4 border-b border-gray-300">
        <div class="flex items-center space-x-4">
        <Dropdown label="Menu" dismissOnClick={false} class="bg-blue-400 hover:bg-blue-500 text-white py-1 font-semibold px-4 rounded">
            <Dropdown.Item>My Events</Dropdown.Item>
            <Dropdown.Item>Chat</Dropdown.Item>
            <Dropdown.Item>Make an Event</Dropdown.Item>
        </Dropdown>
        </div>
        <div class="flex items-center">
        <p class="font-serif font-bold text-5xl italic text-blue-400 mr-40 hover:text-blue-500" onClick = {() => navigate('/')}>YallaNow</p>
        </div>
        <div>
        <button class="bg-blue-400 hover:bg-blue-500 text-white font-semibold py-2.5 px-4 rounded" onClick = {() => navigate('/Login')}>
            Login
        </button>
        </div>
    </nav>
    );
}

export default Nav;
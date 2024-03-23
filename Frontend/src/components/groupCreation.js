import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import { Carousel, Button  } from 'flowbite-react';
import Nav from './nav.js';

function groupCreation() {

    return (
        <div>
            <body class="dark:bg-gray-800 bg-gray-100 min-h-screen">
                <Nav />
                <div class="flex flex-col items-center mt-5">
                    <form class="max-w-lg mx-auto border-2 px-10 py-5 rounded-2xl border-gray-300 bg-white w-full">
                        <p class="text-2xl text-center">Group Creation</p>
                        <div class="mb-5">
                            <label for="text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Group</label>
                            <input type="text" id="GroupName" class="bg-gray-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="Group Name" required />
                        </div>
                        <div class="mb-5">
                            <label for="message" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Description</label>
                            <textarea id="message" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-100 rounded-lg border-1 border-gray-300 focus:ring-blue-500 focus:border-blue-500 " placeholder="Write your group description here..."></textarea>
                        </div>
                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Create Group</button>
                    </form>
                </div>
            </body>
        </div>
    );
}

export default groupCreation;
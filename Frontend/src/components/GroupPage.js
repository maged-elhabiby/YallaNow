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


    if (!group) {   
        return (
            <body class ="bg-gray-100 h-screen">
                {Nav()}
                <div class="flex justify-center items-center pt-20">
                    <p class="test-blue font-serif font-bold text-5xl italic text-gray-500 mr-40" >Error 404 - Group Not Found</p>
                </div>
            </body>
        );
    }
    
    return (
        <body>
            {Nav()}
        </body>
    );
}

export default GroupPage;
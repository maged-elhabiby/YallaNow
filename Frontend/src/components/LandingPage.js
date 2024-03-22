import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';
import '../output.css';
import eventData from './exampleResponse.json';
import { Carousel, Button  } from 'flowbite-react';
import Nav from './nav.js';



function LandingPage() {
    return (
        <div className='main'>
            <div className='nav'>
                <Nav />
            </div>
            <div className='carousel'>
                <button>hello</button>
            </div>
        </div>
    )
}

export default LandingPage;
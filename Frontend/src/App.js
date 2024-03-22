//this file is used for all of the routing in the application
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import LandingPage from './components/LandingPage';
import MainPage from './components/MainPage';
import SingleEvent from './components/SingleEvent';
import Group from './components/GroupPage';



const App = () => {
    return (
        <div>
        <Router>
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/MainPage" element={<MainPage />} />
                <Route path="/Login" element={<Login />} />
                <Route path="/event/:eventID" element={<SingleEvent />}/>
                <Route path="/event" element={<SingleEvent />}/>
                <Route path="/group/:groupID" element={<Group />} />
                <Route path="/group" element={<Group />} />
            </Routes>
        </Router>
        </div>
    );
};

export default App;
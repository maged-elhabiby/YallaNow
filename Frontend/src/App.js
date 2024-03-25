//this file is used for all of the routing in the application
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import MainPage from './components/MainPage';
import SingleEvent from './components/SingleEvent';
import AccountSettings from './components/AccountSettings';
import Paperbase from './components/Paperbase';


const App = () => {
    return (
        <div>
        <Router>
            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/Login" element={<Login />} />
                <Route path="/event/:eventID" element={<SingleEvent />}/>
                <Route path="/event" element={<SingleEvent />}/>
                <Route path="/Settings" element={<Paperbase />} />
            </Routes>
        </Router>
        </div>
    );
};

export default App;
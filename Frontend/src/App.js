//this file is used for all of the routing in the application
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import LandingPage from './components/LandingPage';
import MainPage from './components/MainPage';
import SingleEvent from './components/SingleEvent';
import MyEvents from './components/MyEvents';
import Group from './components/GroupPage';
import GroupCreation from './components/groupCreation';


const App = () => {
    return (
        <div>
        <Router>
            <Routes>
                <Route path="/" element={<LandingPage />} />
                <Route path="/MainPage" element={<MainPage />} />
                <Route path="/Login" element={<Login />} />
                <Route path="/events/:eventID" element={<SingleEvent />}/>
                <Route path="/events" element={<SingleEvent />}/>
                <Route path="/myevents" element={<MyEvents />}/>
                <Route path="/groups/:groupID" element={<Group />} />
                <Route path="/groups" element={<Group />} />
                <Route path="/group-creation" element={<GroupCreation />} />
            </Routes>
        </Router>
        </div>
    );
};

export default App;
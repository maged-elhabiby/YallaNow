import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import ExplorePage from './pages/ExplorePage';

import MyEventsPage from './pages/MyEventsPage';
import EventDetailsPage from './pages/EventDetailsPage';

import SearchPage from './pages/SearchPage';

/*

import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import GroupsPage from './pages/GroupsPage';

<Route path="/login" element={<LoginPage />} />
<Route path="/signup" element={<SignupPage />} />
<Route path="/groups/:groupID" element={<GroupsPage />} />

*/

const App = () => {
  return (
      <Router>
        <div className="h-full" >
          <Navbar />
          <Routes>
              <Route path="/" element={<ExplorePage />} />
              <Route path="/explore" element={<ExplorePage />} />
              <Route path="/myevents" element={<MyEventsPage />}/>
              <Route path="/event-details/:event-id" element={<EventDetailsPage />} />
              <Route path="/search" element={<SearchPage />} />
              {/* Add other routes here */}
          </Routes>
          </div>
      </Router>
  );
};

export default App;

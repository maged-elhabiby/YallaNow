import React from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import ExplorePage from './pages/ExplorePage';
import MyEventsPage from './pages/MyEventsPage';
import EventDetailsPage from './pages/EventDetailsPage';
import SearchPage from './pages/SearchPage';
import SignUpPage from './pages/SignUpPage';
import SignInPage from './pages/SignInPage';
import GroupDetailsPage from './pages/GroupDetailsPage';

const AppContent = () => {
  const location = useLocation();
  const hideNavbarRoutes = ['/signup', '/signin'];

  return (
    <div className="h-full">
      {!hideNavbarRoutes.includes(location.pathname) && <Navbar />}
      <Routes>
        <Route path="/" element={<ExplorePage />} />
        <Route path="/explore" element={<ExplorePage />} />
        <Route path="/myevents" element={<MyEventsPage />} />
        <Route path="/event-details/:event-id" element={<EventDetailsPage />} />
        <Route path="/group/:groupId" element={<GroupDetailsPage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/signin" element={<SignInPage />} />
        {/* Add other routes here */}
      </Routes>
    </div>
  );
};

const App = () => {
  return (
    <Router>
      <AppContent />
    </Router>
  );
};

export default App;

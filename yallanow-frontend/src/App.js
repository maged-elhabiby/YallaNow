import React from 'react';
import { BrowserRouter as Router, Route, Routes , useLocation} from 'react-router-dom';
import Navbar from './components/Navbar';
import ExplorePage from './pages/ExplorePage';
import MyEventsPage from './pages/MyEventsPage';
import EventDetailsPage from './pages/EventDetailsPage';
import SearchPage from './pages/SearchPage';
import SignUpPage from './pages/SignUpPage';
import SignInPage from './pages/SignInPage';
import GroupDetailsPage from './pages/GroupDetailsPage';
import GroupPage from './pages/GroupsPage';
import ForgotPassword from './pages/ForgotPassword';
import CreateGroup from './pages/CreateGroup';
import CreateEvent from './pages/CreateEvent';
import ProtectedRoute from './ProtectedRoute';
import ManageGroupPage from './pages/ManageGroupPage';

const App = () => {
  const location = useLocation();
  const hideNavbarRoutes = ['/signup', '/signin', '/forgotpassword'];
  console.log("Current location:", location.pathname)
  return (
    
      <div className="h-full">
        {/* Assuming Navbar is always visible except on certain routes */}
        {!hideNavbarRoutes.includes(location.pathname) && <Navbar />}

        <Routes>
          {/* Public routes */}
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/signin" element={<SignInPage />} />
          <Route path="/forgotpassword" element={<ForgotPassword />} />
          
          {/* Protected routes */}
          <Route path="/" element={<ProtectedRoute><ExplorePage /></ProtectedRoute>} />
          <Route path="/explore" element={<ProtectedRoute><ExplorePage /></ProtectedRoute>} />
          <Route path="/myevents" element={<ProtectedRoute><MyEventsPage /></ProtectedRoute>} />
          <Route path="/event-details/:event-id" element={<ProtectedRoute><EventDetailsPage /></ProtectedRoute>} />
          <Route path="/groups" element={<ProtectedRoute><GroupPage /></ProtectedRoute>} />
          <Route path="/group/:groupId" element={<ProtectedRoute><GroupDetailsPage /></ProtectedRoute>} />
          <Route path="/search" element={<ProtectedRoute><SearchPage /></ProtectedRoute>} />
          <Route path="/create-group" element={<ProtectedRoute><CreateGroup/></ProtectedRoute>}/>
          <Route path="/create-event/:event-id" element={<ProtectedRoute><CreateEvent/></ProtectedRoute>}/>
          <Route path="/manage-group/:groupId" element={<ProtectedRoute><ManageGroupPage/></ProtectedRoute>}/>
        </Routes>
      </div>
    
  );
};

export default App;

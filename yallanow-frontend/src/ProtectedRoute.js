// ProtectedRoute.js
import React from 'react';
import {Navigate} from 'react-router-dom';
import {useAuth} from './AuthContext';


const ProtectedRoute = ({children}) => {
    const {currentUser, initializing} = useAuth();
    if (initializing) {
        return <div>Loading...</div>;
    }

    if (!currentUser) {
        // Redirect to sign-in page if not authenticated
        return <Navigate to="/signin" replace/>;
    }

    // User is authenticated, render the protected component
    return children;
};

export default ProtectedRoute;

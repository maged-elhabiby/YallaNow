// AuthContext.js
import React, {createContext, useContext, useEffect, useState} from 'react';
import {auth} from './config/firebase-config';

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export const AuthProvider = ({children}) => {
    const [currentUser, setCurrentUser] = useState(null);
    const [initializing, setInitializing] = useState(true);
    useEffect(() => {
        const unsubscribe = auth.onAuthStateChanged(user => {
            console.log("AuthProvider - User state changed:", user);
            setCurrentUser(user);
            setInitializing(false);
        });

        return unsubscribe;
    }, []);

    return (
        <AuthContext.Provider value={{currentUser, initializing}}>
            {!initializing && children}
        </AuthContext.Provider>
    );
};

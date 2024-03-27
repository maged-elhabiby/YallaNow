import React, { useState } from 'react';
import { useAuth } from '../AuthContext'; // Adjust the path as per your structure
import { logoutfirebase, auth } from '../firebase-config'; // Adjust the path as per your structure
import { signOut, updateProfile } from "firebase/auth";
import { Link, useNavigate } from 'react-router-dom';
import {resetPassword} from '../firebase-config';

const ProfilePage = () => {
  const { currentUser } = useAuth(); // Use the context
  const [newDisplayName, setNewDisplayName] = useState(''); // State to hold new display name input by user
  const navigate = useNavigate();
  const [message, setMessage] = useState('');

  const handleSignOut = async (event) => {
    event.preventDefault();
    console.log("we are in signout");
    logoutfirebase();
    navigate('/signin');
  }

  const handleEmailChange = async (e) => {
    e.preventDefault();
    console.log(currentUser?.email)

    try {
        await resetPassword(currentUser?.email);
        setMessage('Password reset email sent! Please check your inbox.');
        
    } catch (error) {
        console.error('Error sending password reset email:', error);
        setMessage('Failed to send password reset email.');
    }
    };



  // Handler for updating profile
  const handleUpdateProfile = async () => {
    if (!newDisplayName.trim()) return; // Basic validation
    try {
      await updateProfile(auth.currentUser, {
        displayName: newDisplayName
      });
      console.log("Profile updated successfully");
      // Update UI accordingly or show a success message
    } catch (error) {
      console.error("Error updating profile:", error);
    }
  };

  return (
    <div className="container mt-20 mx-auto p-6">
        <div className="md:flex md:items-center md:justify-between md:space-x-5">
            <div className="flex items-start space-x-5">
                <div className="flex-shrink-0">
                <div className="relative">
                    <img
                    className="h-16 w-16 rounded-full"
                    src="https://images.unsplash.com/photo-1463453091185-61582044d556?ixlib=rb-=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=8&w=1024&h=1024&q=80"
                    alt=""
                    />
                    <span className="absolute inset-0 rounded-full shadow-inner" aria-hidden="true" />
                </div>
                </div>
                {/*
                Use vertical padding to simulate center alignment when both lines of text are one line,
                but preserve the same layout if the text wraps without making the image jump around.
                */}
                <div className="pt-1.5">
                <h1 className="text-2xl font-bold text-gray-900">{currentUser?.displayName}</h1>
                <p className="text-sm font-medium text-gray-500">{currentUser?.email}</p>
                </div>
            </div>
            <div className="mt-6 flex flex-col-reverse justify-stretch space-y-4 space-y-reverse sm:flex-row-reverse sm:justify-end sm:space-x-3 sm:space-y-0 sm:space-x-reverse md:mt-0 md:flex-row md:space-x-3">
                <button
                type="button"
                className="inline-flex items-center justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                onClick={handleEmailChange}
                >
                Change Password
                </button>
                <button
                type="button"
                className="inline-flex items-center justify-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600" onClick={handleSignOut}
                >
                Sign Out
                </button>
            </div>
            </div>
            {message && (
            <div
            className={`mt-4 p-4 text-center ${message.startsWith('Failed') ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'} rounded-lg`}
            role="alert"
            >
            {message}
            </div>
            )}
    </div>
  );
};

export default ProfilePage;

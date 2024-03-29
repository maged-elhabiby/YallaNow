import React, { useState } from "react";
import { useAuth } from "../AuthContext"; // Adjust the path as per your structure
import { logoutFirebase } from "../config/firebase-config"; // Adjust the path as per your structure
import { signOut, updateProfile } from "firebase/auth";
import { Link, useNavigate } from "react-router-dom";
import { resetPassword } from "../config/firebase-config";

const ProfilePage = () => {
  const { currentUser } = useAuth(); // Use the context
  const [newDisplayName, setNewDisplayName] = useState(""); // State to hold new display name input by user
  const navigate = useNavigate();
  const [message, setMessage] = useState("");

  const handleSignOut = async (event) => {
    event.preventDefault();
    console.log("we are in signout");
    await logoutFirebase();
    navigate("/signin");
  };

  const handleEmailChange = async (e) => {
    e.preventDefault();
    console.log(currentUser?.email);

    try {
      await resetPassword(currentUser?.email);
      setMessage("Password reset email sent! Please check your inbox.");
    } catch (error) {
      console.error("Error sending password reset email:", error);
      setMessage("Failed to send password reset email.");
    }
  };

  // Handler for updating profile
  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    if (!newDisplayName.trim()) {
      setMessage("Display name cannot be empty.");
      return;
    }
    try {
      await updateProfile(currentUser, { displayName: newDisplayName });
      setMessage("Profile updated successfully.");
      setNewDisplayName(""); // Clear input field after update
    } catch (error) {
      console.error("Error updating profile:", error);
      setMessage("Failed to update profile.");
    }
  };

  const displayNameInitial = currentUser?.displayName?.charAt(0).toUpperCase();

  return (
    <div className="container mt-20 mx-auto p-6">
      <div className="mx-auto mb-10 max-w-2xl text-center">
        <p className="text-base font-semibold leading-7 text-pink-600">
          Welcome to
        </p>
        <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">
          Your Profile
        </h2>
        <p className="mt-6 text-lg leading-8 text-gray-600">
          View Account and Manage your account settings and preferences
        </p>
      </div>
      <div className="flex flex-col items-center space-y-4">
        <div className="flex items-center space-x-4">
          <div className="flex-shrink-0 h-16 w-16 rounded-full bg-pink-500 flex items-center justify-center text-white font-bold text-xl">
            {currentUser.photoURL ? (
              <img
                className="rounded-full"
                src={currentUser.photoURL}
                alt="Profile"
              />
            ) : (
              <span>{displayNameInitial}</span>
            )}
          </div>
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              {currentUser?.displayName}
            </h1>
            <p className="text-sm font-medium text-gray-500">
              {currentUser?.email}
            </p>
          </div>
        </div>

        <form onSubmit={handleUpdateProfile} className="w-full max-w-xs">
          <input
            type="text"
            placeholder="New display name"
            value={newDisplayName}
            onChange={(e) => setNewDisplayName(e.target.value)}
            className="block w-full rounded-md border-gray-300 shadow-sm focus:border-pink-500 focus:ring-pink-500 sm:text-sm"
          />
          <div className="mt-4 flex justify-center space-x-2">
            <button
              type="submit"
              className="inline-flex justify-center rounded-md border border-transparent bg-pink-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-pink-700 focus:outline-none focus:ring-2 focus:ring-pink-500 focus:ring-offset-2"
            >
              Update Profile
            </button>

            <button
              className="px-3 py-2 rounded-md bg-gray-300 text-sm font-semibold text-gray-900 hover:bg-gray-400"
              onClick={handleEmailChange}
            >
              Change Password
            </button>
          </div>
        </form>
        <div className="mt-4 space-x-2">
          <button
            className="px-3 py-2 rounded-md bg-pink-600 text-sm font-semibold text-white hover:bg-pink-700"
            onClick={handleSignOut}
          >
            Sign Out
          </button>
        </div>
      </div>
      {message && (
        <div
          className={`mt-4 w-full text-center ${
            message.startsWith("Failed")
              ? "text-red-700 bg-red-100"
              : "text-green-700 bg-green-100"
          } p-4 rounded-lg`}
          role="alert"
        >
          {message}
        </div>
      )}
    </div>
  );
};

export default ProfilePage;

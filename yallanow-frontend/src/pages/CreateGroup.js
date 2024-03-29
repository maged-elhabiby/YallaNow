import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import GroupService from '../api/GroupService';
import { useAuth } from '../AuthContext';

function generateRandomId(length = 8) {
    return [...Array(length)].map(() => Math.floor(Math.random() * 16).toString(16)).join('');
  }

const CreateGroup = () => {
  const [groupName, setGroupName] = useState('');
  const [isPrivate, setIsPrivate] = useState(false);
  const navigate = useNavigate();
  const { currentUser } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Assuming "yourCurrentUserId" is obtained from your auth context or a similar state management solution
    const userId = currentUser?.uid;
    const userName = currentUser?.displayName || 'Anonymous'; // Fallback to 'Anonymous' or handle appropriately

    const groupData = {
      groupName, 
      isPrivate,
      groupMembers: [{
        userID: userId,
        userName: userName, 
        role: "ADMIN"
      }],
      events: [] 
    };

    try {
      const newGroup = await GroupService.createGroup(groupData);
      console.log('Group created successfully', newGroup);
      navigate(`/group/${newGroup.groupID}`);
    } catch (error) {
      console.error('Error creating group:', error);
    }
  };


  return (
    <div className="max-w-md mx-auto mt-10">
        <div className=" px-2 py-10 sm:py-10 lg:px-2">
            <div className="mx-auto max-w-2xl text-center">
                <p className="text-base font-semibold leading-7 text-pink-600">Become a Founder</p>
                <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">Create Your Dream Group</h2>
                <p className="mt-6 text-lg leading-8 text-gray-600">
                Build you dream Community of likeminded Individuals
                </p>
            </div>
        </div>
      <h2 className="text-2xl font-semibold mb-5">Create a New Group</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="groupName" className="block text-sm font-medium text-gray-700">Group Name</label>
          <input
            type="text"
            name="groupName"
            id="groupName"
            required
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-pink-500 focus:ring-pink-500 sm:text-sm"
            value={groupName}
            onChange={(e) => setGroupName(e.target.value)}
          />
        </div>
        <div className="flex items-center">
          <input
            id="isPrivate"
            name="isPrivate"
            type="checkbox"
            className="h-4 w-4 text-pink-600 focus:ring-pink-500 border-gray-300 rounded"
            checked={isPrivate}
            onChange={(e) => setIsPrivate(e.target.checked)}
          />
          <label htmlFor="isPrivate" className="ml-2 block text-sm text-gray-900">
            Private Group
          </label>
        </div>
        <button
          type="submit"
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-pink-600 hover:bg-pink-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-pink-500"
        >
          Create Group
        </button>
      </form>
    </div>
  );
};

export default CreateGroup;

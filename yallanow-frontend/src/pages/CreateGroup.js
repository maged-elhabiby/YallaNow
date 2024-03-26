import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function generateRandomId(length = 8) {
    return [...Array(length)].map(() => Math.floor(Math.random() * 16).toString(16)).join('');
  }

const CreateGroup = () => {
  const [groupName, setGroupName] = useState('');
  const groupId = "1";
  const [isPrivate, setIsPrivate] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const currentUserId = "yourCurrentUserId";

    // Here, you'll handle the group creation logic
    console.log({
      groupId,
      groupName,
      isPrivate,
      members: [{
        userId: currentUserId,
        role: "ADMIN"
      }]

    });
    setTimeout(() => {
        // alert(`Group "${groupName}" created with ID: ${groupId}`);
        // Navigate to the group's detail page
        navigate(`/group/${groupId}`);
      }, 500);  
};


  return (
    <div className="max-w-md mx-auto mt-10">
        <div className=" px-2 py-10 sm:py-10 lg:px-2">
            <div className="mx-auto max-w-2xl text-center">
                <p className="text-base font-semibold leading-7 text-indigo-600">Become a Founder</p>
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
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
            value={groupName}
            onChange={(e) => setGroupName(e.target.value)}
          />
        </div>
        <div className="flex items-center">
          <input
            id="isPrivate"
            name="isPrivate"
            type="checkbox"
            className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
            checked={isPrivate}
            onChange={(e) => setIsPrivate(e.target.checked)}
          />
          <label htmlFor="isPrivate" className="ml-2 block text-sm text-gray-900">
            Private Group
          </label>
        </div>
        <button
          type="submit"
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Create Group
        </button>
      </form>
    </div>
  );
};

export default CreateGroup;

// components/GroupPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import GroupCard from '../components/GroupCard';
import GroupService from '../api/GroupService';

const GroupPage = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const fetchedGroups = await GroupService.getGroups();
        console.log(fetchedGroups); // Log the fetched groups

        setGroups(fetchedGroups);
        console.log('Works')
      } catch (error) {
        console.error('Error fetching groups:', error);
        console.log('Fail')
        // Optionally, update state or UI to inform the user about the error
      }
    };
  
    fetchGroups();
  }, []);
  
  console.log(groups)

  return (
    <div>
      <div className="py-4 sm:py-20">
        <div className="mx-auto max-w-8xl px-10 lg:px-10">
          {/* Flex container for the heading and the button */}
          <div className="flex justify-between items-center">
            <div>
              <p className="text-base font-semibold leading-7 text-pink-600 mb-2">Find People Like You</p>
              <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl">Groups Center</h2>
            </div>
            {/* Link wrapping the button for navigation */}
            <Link to="/create-group" className="inline-flex items-center px-4 py-2 border border-transparent text-base font-medium rounded-md text-white bg-pink-600 hover:bg-pink-700">
              Create a Group
            </Link>
          </div>
          <p className="mt-4 text-base leading-7 text-gray-600">
            Join, Create, Explore and Find Groups of People like you
          </p>
        </div>
      </div>
      <div className="flex flex-wrap justify-center gap-4">
        {groups.map(group => (
          <GroupCard key={group.groupID} group={group} />
        ))}
      </div>
    </div>
  );
};

export default GroupPage;
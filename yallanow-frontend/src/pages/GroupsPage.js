// components/GroupPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import GroupCard from '../components/GroupCard';

const GroupPage = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    // Fetch the list of groups. This is a mock and should be replaced with a real fetch request.
    const mockGroups = [
      { id: 1, name: 'Astrophysics Enthusiasts',numberOfPeople: 150, },
      { id: 2, name: 'Quantum Computing Circle',numberOfPeople: 190, },
    ];
    setGroups(mockGroups);
  }, []);

  return (
    <div>
      <div className="py-4 sm:py-20">
        <div className="mx-auto max-w-8xl px-10 lg:px-10">
          {/* Flex container for the heading and the button */}
          <div className="flex justify-between items-center">
            <div>
              <p className="text-base font-semibold leading-7 text-indigo-600 mb-2">Find People Like You</p>
              <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl">Groups Center</h2>
            </div>
            {/* Link wrapping the button for navigation */}
            <Link to="/create-group" className="inline-flex items-center px-4 py-2 border border-transparent text-base font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700">
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
          <GroupCard key={group.id} group={group} />
        ))}
      </div>
    </div>
  );
};

export default GroupPage;
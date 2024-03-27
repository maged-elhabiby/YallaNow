
import React, { useState, useEffect } from 'react';
import { Link,useNavigate } from 'react-router-dom';
import GroupCard from '../components/GroupCard';


function MyGroupsPage() {
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
      <div className="px-6 py-24 sm:py-32 lg:px-8">
        <div className="mx-auto max-w-2xl text-center">
          <p className="text-base font-semibold leading-7 text-pink-600">Welcome to Your</p>
          <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">Communities</h2>
          <p className="mt-6 text-lg leading-8 text-gray-600">
            All The Communities That You Have Joined
          </p>
        </div>
        <div className="flex flex-wrap justify-center gap-4">
        {groups.map(group => (
          <GroupCard key={group.id} group={group} />
        ))}
      </div>
      </div>
    )
  }

  export default MyGroupsPage;
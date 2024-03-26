// components/GroupPage.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const GroupPage = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    // Fetch the list of groups. This is a mock and should be replaced with a real fetch request.
    const mockGroups = [
      { id: 1, name: 'Astrophysics Enthusiasts' },
      { id: 2, name: 'Quantum Computing Circle' },
      // Add more groups as needed
    ];
    setGroups(mockGroups);
  }, []);

  return (
    <div>
      <h1>Groups</h1>
      <ul>
        {groups.map(group => (
          <li key={group.id}>
            <Link to={`/groups/${group.id}`}>{group.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default GroupPage;

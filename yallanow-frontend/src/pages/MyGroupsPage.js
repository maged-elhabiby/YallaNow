import React, { useState, useEffect } from 'react';
import { Link,useNavigate } from 'react-router-dom';
import GroupCard from '../components/GroupCard';
import groupService from '../api/groupService';
import { useAuth } from '../AuthContext';

function MyGroupsPage() {
    const [groups, setGroups] = useState([]);
    const { currentUser } = useAuth();
    const userId = currentUser?.uid;

    useEffect(() => {
      const fetchUserGroups = async () => {
        try {
          const fetchedGroups = await groupService.getGroupByUserID(userId);
          setGroups(fetchedGroups);
        } catch (error) {
          console.error('Error fetching groups:', error);
        }
      };
    
      fetchUserGroups();
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
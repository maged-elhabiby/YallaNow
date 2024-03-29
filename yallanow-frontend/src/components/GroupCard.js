// GroupCard.js
import React from 'react';
import { useNavigate } from 'react-router-dom';

const GroupCard = ({ group }) => {
  const navigate = useNavigate();

  // Function to handle click on the card
  const handleClick = () => {
    navigate(`/group/${group.groupID}`);
  };
  console.log(group);
  return (
    <div
      className="max-w-sm rounded overflow-hidden shadow-lg cursor-pointer m-4"
      onClick={handleClick}
    >
      <div className="px-6 py-4">
        <div className="font-bold text-xl mb-2">{group.groupName}</div>
        <p className="text-gray-700 text-base">
          Members: {group.memberCount}
        </p>
        <p className="text-gray-700 text-base">
          Status: {group.isPrivate ? 'Private' : 'Public'}
        </p>
      </div>
    </div>
  );
};

export default GroupCard;

import React from 'react';
import { useNavigate,useLocation } from 'react-router-dom';

function ManageGroupPage() {
  const navigate = useNavigate();
  const location = useLocation();

  const groupData = location.state.groupData; 
  // Function to handle role update
  const handleRoleUpdate = (userId, newRole) => {
    console.log(`Update role for user ${userId} to ${newRole}`);
    // Implement role update logic here
  };

  // Function to handle user removal
  const handleRemoveUser = (userId) => {
    console.log(`Remove user ${userId} from group`);
    // Implement removal logic here, ensuring at least one admin remains
  };

  return (
    <div className="mt-20 px-4 sm:px-6 lg:px-8">
      <div className="md:flex md:items-center md:justify-between">
      <div className="min-w-0 flex-1">
        <h2 className="text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">
          Manage Group
        </h2>
      </div>
      <div className="mt-4 flex md:ml-4 md:mt-0">
        <button
          type="button"
          onClick={() => navigate(-1)}
          className="ml-3 inline-flex items-center rounded-md bg-pink-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-pink-700 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-pink-600"
        >
          Go Back
        </button>
      </div>
    </div>
      <h1 className="text-xl font-semibold text-gray-900">{groupData.groupName}</h1>
      <p className="mt-1 text-sm text-gray-600">Manage your group members and their roles.</p>
      <div className="mt-4">
        <div className="-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
          <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Name
                  </th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Email
                  </th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Role
                  </th>
                  <th scope="col" className="relative px-6 py-3">
                    <span className="sr-only">Edit</span>
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {groupData.groupMembers.map((member) => (
                  <tr key={member.userID}>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{member.userName}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{member.email}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{member.role}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                      {member.role !== 'ADMIN' || (member.role === 'ADMIN' && groupData.groupMembers.filter(m => m.role === 'ADMIN').length > 1) ? (
                        <>
                          <button onClick={() => handleRoleUpdate(member.userID, member.role === 'ADMIN' ? 'USER' : 'ADMIN')} className="text-indigo-600 hover:text-indigo-900 mr-4">
                            {member.role === 'ADMIN' ? 'Demote' : 'Promote'}
                          </button>
                          <button onClick={() => handleRemoveUser(member.userID)} className="text-red-600 hover:text-red-900">
                            Remove
                          </button>
                        </>
                      ) : null}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ManageGroupPage;

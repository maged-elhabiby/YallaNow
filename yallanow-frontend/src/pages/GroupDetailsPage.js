// GroupDetailsPage.js
import React, { useEffect, useState } from 'react';
import { useParams,useNavigate, Link  } from 'react-router-dom';
import EventCard from '../components/EventCard';
import GroupService from '../api/GroupService';
import EventService from '../api/EventService';
import groupMemeberService from '../api/GroupMemeberService';
import { useAuth } from '../AuthContext';

const GroupDetailsPage = () => {
    const { groupId } = useParams();
    const [groupDetails, setGroupDetails] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [events, setEvents] = useState([]);
    const navigate = useNavigate();
    const { currentUser } = useAuth();

    const groupMembers = {
        userID: currentUser?.uid,
        userName: currentUser?.displayName, 
        role: "USER"
    };

    const fetchGroupDetails = async () => {
            try {
                const details = await GroupService.getGroup(groupId);
                setGroupDetails(details);
                const groupEvents = await EventService.getEventsForGroup(groupId);
                setEvents(groupEvents);
            } catch (error) {
                console.error("Error fetching group details:", error);
            }
    };
    useEffect(() => {
        fetchGroupDetails();
    }, [groupId]);

    if (!groupDetails) {
        return <div>Loading...</div>;
    }
    const handleManageGroup = () => {
        if (isAdmin) {
            console.log(groupDetails)
            navigate(`/manage-group/${groupId}`,{ state: { groupData: groupDetails } });
        } else {
            alert("You do not have permission to manage this group.");
        }
    };

    const handleJoinGroup = async () => {
        try {
            await groupMemeberService.addGroupMember(groupId, groupMembers);
            alert('Successfully joined the group!');
            fetchGroupDetails();
        } catch (error) {
            console.error('Error joining group:', error);
            alert('Failed to join the group.');
        }
    };
    
    const handleCreateEvent = () => {
        if (!currentUser) {
            navigate('/login'); 
        } else {
            navigate(`/create-event/${groupId}`);
        }
    };

    const isAdmin = groupDetails.groupMembers.some(member => member.userID === currentUser?.uid && member.role === 'ADMIN');
    const isMember = groupDetails.groupMembers.some(member => member.userID === currentUser?.uid);
    console.log(isMember,isAdmin,groupDetails.isPrivate)
    return (
    <div className="container mx-auto py-10">
        <div className="px-6 py-20 sm:py-10 lg:px-8">
            <div className="mx-auto max-w-2xl text-center">
                <p className="text-base font-semibold leading-7 text-pink-600">Welcome to</p>
                <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">{groupDetails.groupName}</h2>
                <p className="mt-6 text-lg leading-8 text-gray-600">{groupDetails.isPrivate ? 'Private' : 'Public'} Group</p>
            </div>
            <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                {/* Conditional rendering based on user role and if user is a member */}
                <div className="flex justify-center mt-4">
                {isAdmin && (
                    <button
                        onClick={handleManageGroup}
                        className="mt-4 px-4 py-2 bg-pink-600 text-white rounded hover:bg-pink-700 transition-colors"
                    >
                        Manage Group
                    </button>
                )}
                {isMember && (
                    <button
                        onClick={() => setIsModalOpen(true)}
                        className="ml-2 mt-4 mr-2 px-4 py-2 bg-pink-600 text-white rounded hover:bg-pink-700 transition-colors"
                    >
                        View Members
                    </button>
                )}
                {!groupDetails.isPrivate && !isMember && (
                <button
                    onClick={handleJoinGroup}
                    className="mt-4 mr-2 px-4 py-2 bg-pink-600 text-white rounded hover:bg-pink-700 transition-colors"
                >
                    Join Group
                </button>
                )}
                {isMember && (
                <button
                        className="mt-4 mr-2 px-4 py-2 bg-pink-600 text-white rounded hover:bg-pink-700 transition-colors"
                        onClick={handleCreateEvent}
                    >
                        Create Event
                </button>
                )}
                </div>

                {isModalOpen && (
                    <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
                        <div className="bg-white p-4 rounded-lg shadow-lg w-full max-w-md">
                            <h2 className="mb-2 text-lg font-semibold text-gray-900">Members List</h2>
                            <ul>
                                {groupDetails.groupMembers.map((member) => (
                                    <li key={member.userID} className="border-b-2 py-4">{member.userName} - {member.role}</li>
                                ))}
                            </ul>
                            <div className="text-right">
                                <button
                                    onClick={() => setIsModalOpen(false)}
                                    className="mt-5 px-4 py-2 bg-pink-600 text-white rounded hover:bg-pink-700 transition-colors"
                                >
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                )}

            </div>
        </div>
        <div className="mx-auto max-w-9xl px-4 sm:px-6 lg:px-8">
            <h2 className="text-xl font-semibold">Events</h2>
            <div className="ml-[-2rem] overflow-auto">
                <div className="flex flex-row gap-5 p-4">
                    {events.map((event) => (
                        <EventCard key={event.eventId} event={event} />
                    ))}
                </div>
            </div>
        </div>
    </div>

    );
};

export default GroupDetailsPage;

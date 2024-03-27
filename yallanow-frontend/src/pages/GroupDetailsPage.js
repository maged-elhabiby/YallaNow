// GroupDetailsPage.js
import React, { useEffect, useState } from 'react';
import { useParams,useNavigate, Link  } from 'react-router-dom';
import EventCard from '../components/EventCard';
import getMockRecommendations from '../data/readRecommendationData';

const GroupDetailsPage = () => {
    const { groupId } = useParams(); // Assuming 'group-id' from your route
    const [groupDetails, setGroupDetails] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [events, setEvents] = useState([]);
    const navigate = useNavigate(); // Add this line

    useEffect(() => {
        // Simulate fetching group details based on groupId
        // In a real app, you'd fetch this data from a backend service
        const fetchGroupDetails = async () => {
            // This is mock data; replace it with a fetch call to your backend
            const mockData = {
                groupID: 1,
                groupName: "Astrophysics Enthusiasts",
                isPrivate: true,
                groupMembers: [
                    { userID: 2, role: "ADMIN" },
                    { userID: 3, role: "MEMBER" },
                ],
                events: [
                    { eventID: 101, eventName: "Black Hole Mysteries" },
                    { eventID: 102, eventName: "The Expanding Universe" },
                ],
            };
            if (parseInt(groupId) === mockData.groupID) {
                setGroupDetails(mockData);
            }
        };

        fetchGroupDetails();
    }, [groupId]);
    
    useEffect(() => {
        const formattedEvents = getMockRecommendations();
        setEvents(formattedEvents.recommendations);
    }, []);
    if (!groupDetails) {
        return <div>Loading...</div>;
    }
    const handleCreateEvent = () => {
        navigate(`/create-event/${groupId}`); // Adjust the path as necessary
    };
    return (
        <div className="container mx-auto p-4">
            <div className="px-6 py-24 sm:py-32 lg:px-8">
                <div className="mx-auto max-w-2xl text-center">
                    <p className="text-base font-semibold leading-7 text-indigo-600">Welcome to</p>
                    <h2 className="mt-2 text-4xl font-bold tracking-tight text-gray-900 sm:text-6xl">{groupDetails.groupName}</h2>
                    <p className="mt-6 text-lg leading-8 text-gray-600"> {groupDetails.isPrivate ? 'Private' : 'Public'} Group </p>
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                    {/**
                    groupDetails.isMember && (
                        <button className="mt-4 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700 transition-colors">View Members</button>
                    )
                    */}
                    <button
                            onClick={() => setIsModalOpen(true)}
                            className="mt-4 mr-2 px-4 py-2 bg-green-500 text-white rounded hover:bg-green-700 transition-colors"
                        >
                            View Members
                        </button>

                        {isModalOpen && (
                        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
                            <div className="bg-white p-4 rounded-lg shadow-lg w-full max-w-md">
                                <h2 className="mb-2 text-lg font-semibold text-black-900 dark:text-black">Members List</h2>
                                <ul className="w-96 text-surface dark:text-black">
                                    {groupDetails.groupMembers.map((member) => (
                                        <li className="w-full border-b-2 border-neutral-100 py-4 dark:border-black/10" key={member.userID}>User {member.userID} - {member.role}</li>
                                    ))}
                                </ul>
                                <div className="text-right">
                                    <button
                                        onClick={() => setIsModalOpen(false)}
                                        className="mt-5 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700 transition-colors"
                                    >
                                        Close
                                    </button>
                                </div>
                            </div>
                        </div>
                        )}             
                    {/* Conditional rendering for Join/Leave button */}
                    {groupDetails.isMember ? (
                        <button className="mt-4 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700 transition-colors">Leave Group</button>
                    ) : (
                        <button className="mt-4 px-4 py-2 bg-green-500 text-white rounded hover:bg-green-700 transition-colors">Join Group</button>
                    )}
                    </div>
                    <button onClick={handleCreateEvent}className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700 transition-colors"><Link to={`/create-event/${groupId}`} state={{ groupId }}>Create Event</Link></button>
                    {groupDetails.isMember ? (
                        <button onClick={handleCreateEvent}className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700 transition-colors"><Link to={`/create-event/${groupId}`} state={{ groupId }}>Create Event</Link></button>
                    ) : (<></>)}
               
                </div>
            </div>
            <div className="mx-auto max-w-9xl px-4 sm:px-6 lg:px-8">
                <h2 className="text-xl font-semibold">Events</h2>
                <div className="ml-[-2rem] overflow-auto">
                <div className="flex flex-row gap-5 p-4">
                    {events.map((event) => (
                        <EventCard key={event.eventId} event={event} className="flex-grow w-full" />
                    ))}
                </div>
                </div>

            </div>
        </div>
    );
};

export default GroupDetailsPage;

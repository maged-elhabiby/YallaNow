import React from 'react';
import { Fragment, useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import { Disclosure, Menu, Transition } from '@headlessui/react'
import { MagnifyingGlassIcon } from '@heroicons/react/20/solid'
import { Bars3Icon, BellIcon, XMarkIcon } from '@heroicons/react/24/outline'
import { logoutfirebase, auth } from '../config/firebase-config';
import { useAuth } from '../AuthContext';

function classNames(...classes) {
  return classes.filter(Boolean).join(' ')
}



const Navbar = () => {

  const { currentUser } = useAuth();
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?query=${encodeURIComponent(searchQuery.trim())}`);
    }
  };
  const handleProfile = (event) => {
    event.preventDefault(); // Prevent default link behavior
    navigate('/profile'); // Programmatically navigate to /profile
  };


  useEffect(() => {
    if (!currentUser) {
      navigate('/signin');
    }
  }, [currentUser, navigate]);


  const handleSignOut = async (event) => {
    event.preventDefault();
    console.log("we are in signout");
    logoutfirebase();
    navigate('/signin');
  }

  const displayNameInitial = currentUser?.displayName?.charAt(0).toUpperCase();

  return (
    <Disclosure as="nav" className="bg-gray-800 fixed top-0 w-full z-50">
      {({ open }) => (
        <>
          <div className="mx-auto max-w-7xl px-2 sm:px-4 lg:px-8">
            <div className="relative flex h-16 items-center justify-between">
              <div className="flex items-center px-2 lg:px-0">
                <div className="flex-shrink-0">
                  <img
                    className="h-12 w-auto"
                    src="https://storage.googleapis.com/tmp-bucket-json-data/Logo.svg"
                    alt="Yala"
                  />
                </div>
                <div className="hidden lg:ml-6 lg:block">
                  <div className="flex space-x-4">
                    <Link to="/explore" className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-pink-600 hover:text-white">
                      Explore
                    </Link>
                    <Link to="/myevents" className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-pink-600 hover:text-white">
                      My Events
                    </Link>
                    <Link to="/groups" className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-pink-600 hover:text-white">
                      Groups
                    </Link>
                    <Link to="/mygroups" className="rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-pink-600 hover:text-white">
                      MyGroups
                    </Link>
                  </div>
                </div>
              </div>
              <div className="flex flex-1 justify-center px-2 lg:ml-6 lg:justify-end">
                <form onSubmit={handleSearch} className="w-full max-w-lg lg:max-w-xs">
                  <label htmlFor="search" className="sr-only">
                    Search
                  </label>
                  <div className="relative">
                    <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                      <MagnifyingGlassIcon className="h-5 w-5 text-pink-400" aria-hidden="true" />
                    </div>
                    <input
                      id="search"
                      name="search"
                      className="block w-full rounded-md border-0 bg-gray-700 py-1.5 pl-10 pr-3 text-gray-300 placeholder:text-gray-400 focus:bg-white focus:text-gray-900 focus:ring-0 sm:text-sm sm:leading-6"
                      placeholder="Search"
                      type="search"
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                    />
                  </div>
                </form>
              </div>
              <div className="flex lg:hidden">
                {/* Mobile menu button */}
                <Disclosure.Button className="relative inline-flex items-center justify-center rounded-md p-2 text-pink-400 hover:bg-pink-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                  <span className="absolute -inset-0.5" />
                  <span className="sr-only">Open main menu</span>
                  {open ? (
                    <XMarkIcon className="block h-6 w-6" aria-hidden="true" />
                  ) : (
                    <Bars3Icon className="block h-6 w-6" aria-hidden="true" />
                  )}
                </Disclosure.Button>
              </div>
              <div className="hidden lg:ml-4 lg:block">
                <div className="flex items-center">
                  {/* Profile dropdown */}
                  {/* This need logic for logged in / logged out */}
                  <Menu as="div" className="relative ml-4 flex-shrink-0">
                    <div>
                      <Menu.Button className="relative flex rounded-full bg-gray-800 text-sm text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
                        <span className="absolute -inset-1.5" />
                        <span className="sr-only">Open user menu</span>
                        {currentUser?.photoURL ? (
                        <img className="h-8 w-8 rounded-full" src={currentUser?.photoURL} alt="" />
                      ) : (
                        <span className="inline-flex items-center justify-center h-8 w-8 rounded-full bg-gray-500">
                          <span className="text-sm font-medium leading-none text-white">{displayNameInitial}</span>
                        </span>
                )}
                      </Menu.Button>
                    </div>
                    <Transition
                      as={Fragment}
                      enter="transition ease-out duration-100"
                      enterFrom="transform opacity-0 scale-95"
                      enterTo="transform opacity-100 scale-100"
                      leave="transition ease-in duration-75"
                      leaveFrom="transform opacity-100 scale-100"
                      leaveTo="transform opacity-0 scale-95"
                    >
                      <Menu.Items className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                        <Menu.Item>
                          {({ active }) => (
                            <Link to="/profile" className={classNames(active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700')}>
                              Your Profile
                            </Link>
                          )}
                        </Menu.Item>
                        <Menu.Item>
                          {({ active }) => (
                            <Link to="/signout" className={classNames(active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700' )} onClick={handleSignOut}>
                              Sign out
                            </Link>
                          )}
                        </Menu.Item>
                      </Menu.Items>
                    </Transition>
                  </Menu>
                </div>
              </div>
            </div>
          </div>

          <Disclosure.Panel className="lg:hidden">
            <div className="space-y-1 px-2 pb-3 pt-2">
              {/* Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" */}
              <Disclosure.Button
                as={Link}
                to="/explore"
                className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-pink-600 hover:text-white"
              >
                Explore
              </Disclosure.Button>
              <Disclosure.Button
                as={Link}
                to="/myevents"
                className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-pink-600 hover:text-white"
              >
                My Events
              </Disclosure.Button>
              <Disclosure.Button
                as={Link}
                to="/groups"
                className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-pink-600 hover:text-white"
              >
                Groups
              </Disclosure.Button>
              <Disclosure.Button
                as={Link}
                to="/mygroups"
                className="block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-pink-600 hover:text-white"
              >
                My Groups
              </Disclosure.Button>
            </div>
            <div className="border-t border-gray-700 pb-3 pt-4">
              <div className="flex items-center px-5">
                <div className="flex-shrink-0">
                {currentUser?.photoURL ? (
                  <img className="h-8 w-8 rounded-full" src={currentUser?.photoURL} alt="" />
                ) : (
                  <span className="inline-flex items-center justify-center h-8 w-8 rounded-full bg-gray-500">
                    <span className="text-sm font-medium leading-none text-white">{displayNameInitial}</span>
                  </span>
                )}
                </div>
                <div className="ml-3">
                  <div className="text-base font-medium text-white">{currentUser?.displayName}</div>
                  <div className="text-sm font-medium text-gray-400">{currentUser?.email}</div>
                </div>
              </div>
              <div className="mt-3 space-y-1 px-2">
                <Disclosure.Button
                  as="a"
                  href="#"
                  className="block rounded-md px-3 py-2 text-base font-medium text-gray-400 hover:bg-pink-600 hover:text-white" 
                  onClick={handleProfile}
                >
                  Your Profile
                </Disclosure.Button>
                <Disclosure.Button
                  as="a"
                  href="#"
                  className="block rounded-md px-3 py-2 text-base font-medium text-gray-400 hover:bg-pink-600 hover:text-white" onClick={handleSignOut}
                >
                  Sign out
                </Disclosure.Button>
              </div>
            </div>
          </Disclosure.Panel>
        </>
      )}
    </Disclosure>
  )
}

export default Navbar;
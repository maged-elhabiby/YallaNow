// src/components/Login.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import axios from 'axios';
import { Link } from 'react-router-dom';
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";
import { Login, logoutfirebase, googleSignIn } from '../firebase-config';
import ForgotPassword from './ForgotPassword';

const SignInPage = () => {
  const navigate = useNavigate();
  const [loginMessage, setLoginMessage] = useState({ text: '', isError: false });

  // Handles Forgot Password
  const handleForgotPassword = () => {
    navigate('/forgotpassword');
  };

  //Handles Google SignIn
  const handleGoogleSignIn = async () => {
    if (await googleSignIn() === true) {
      console.log("Login successful");
      navigate('/explore');
    } else {
      console.log("Login failed");
    
    }
  };
  
    // Handler for form submission
    const handleSubmit = async (event) => {
      event.preventDefault(); // Prevent default form submission
  
      // Extract email and password from the form
      const email = event.target.email.value;
      const password = event.target.password.value;
      
      // Log email and password to the console
      console.log("Email:", email, "Password:", password);
      const loginData = { "email": email, "password": password};

      if (await Login(loginData) === true) {
        console.log("Login successful");
        navigate('/explore');
      } else {
        console.log("Login failed");
        setLoginMessage({ text: 'Login failed. Invalid email or password.', isError: true });
      }
    };

  return (
    <>

      <div className="mt-24 flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-20 w-auto"
            src="https://storage.googleapis.com/tmp-bucket-json-data/Logo.svg"
            alt="YallaNow"
          />
          <h2 className="mt-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
            Sign in to your account
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" onSubmit={handleSubmit} method="POST">
            <div>
              <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                Email address
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  name="email"
                  type="email"
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-pink-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
                <div className="text-sm">
                  {/* <a href="#" className="font-semibold text-pink-600 hover:text-pink-500">
                    Forgot password?
                  </a> */}
                  <button type="button" onClick={handleForgotPassword} className="text-sm font-semibold text-pink-600 hover:text-pink-500">
                  Forgot password?
                </button>
                </div>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-pink-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-pink-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-pink-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-pink-600"
              >
                Sign in
              </button>
            </div>
            
          </form>
          <div style={{ paddingTop: '10px' }}>
            <button
              type="button"
              onClick={handleGoogleSignIn}
              className="flex items-center justify-center w-full rounded-md bg-white border border-gray-300 px-3 py-1.5 text-sm font-semibold leading-6 text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:border-pink-500 focus:ring-2 focus:ring-offset-2 focus:ring-pink-500"
            >
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png"
                alt="Google Logo"
                className="h-5 w-5 mr-2"
              />
              Sign in with Google
            </button>
          </div>
          {loginMessage.text && (
          <div
            className={`mt-3 mx-auto max-w-sm text-center ${loginMessage.isError ? 'text-red-700 bg-red-100' : 'text-green-700 bg-green-100'} p-4 rounded-lg mb-4`}
            role="alert"
          >
            {loginMessage.text}
          </div>
        )}
          <p className="mt-10 text-center text-sm text-gray-500">
            Not a member?{' '}
            <Link to="/signup" className="font-semibold leading-6 text-pink-600 hover:text-pink-500">
            Sign Up, Today!
            </Link>
          </p>
        </div>
      </div>
    </>
  )
}

export default SignInPage
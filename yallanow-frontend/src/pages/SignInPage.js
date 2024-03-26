// src/components/Login.js
import React from 'react';

// import {Register, Login, logout, resetPassword} from '../firebase-config';
//import { useAuth } from '../AuthContext.js';
import { useNavigate } from 'react-router-dom';


import axios from 'axios';
import { Link } from 'react-router-dom';
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";
import { Login, logoutfirebase } from '../firebase-config';



// function postData() {
//   const url = `http://127.0.0.1:8080/api/test`;
//   const data = { /* Your data object */ };

//   axios.post(url, data, {
//     headers: {
//       'Content-Type': 'application/json',
//       "Authorization": localStorage.getItem('idToken')
//       // Add any additional headers here if needed
//     }
//   })
//     .then(response => {
//       // Handle the response data
//       console.log(response.data);
//     })
//     .catch(error => {
//       // Handle errors here
//       console.error('There was a problem with your axios request:', error);
//     });
//   }






const SignInPage = () => {

  const navigate = useNavigate();
  //const { signInWithGoogle } = useAuth();
  const handleGoogleSignIn = () => {
    const auth = getAuth();
    const provider = new GoogleAuthProvider(); // Create Google provider object

    signInWithPopup(auth, provider) // Sign in with Google using popup
      .then((result) => {
        // Handle successful sign-in here
        console.log('Google Sign-In successful');
        console.log('User info:', result.user);
      })
      .catch((error) => {
        // Handle errors here
        console.error('Google Sign-In failed:', error);
      });
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
        navigate('/');
      } else {
        console.log("Login failed");
        alert("Login failed");
      }

      // Here, you can also add your sign-in logic with email and password
    };
  return (
    <>

      <div className="mt-24 flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-10 w-auto"
            src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
            alt="Your Company"
          />
          <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
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
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
                <div className="text-sm">
                  <a href="#" className="font-semibold text-indigo-600 hover:text-indigo-500">
                    Forgot password?
                  </a>
                </div>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Sign in
              </button>
            </div>
            
          </form>
          <div style={{ paddingTop: '10px' }}>
            <button
              type="button"
              onClick={handleGoogleSignIn}
              className="flex items-center justify-center w-full rounded-md bg-white border border-gray-300 px-3 py-1.5 text-sm font-semibold leading-6 text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:border-indigo-500 focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png"
                alt="Google Logo"
                className="h-5 w-5 mr-2"
              />
              Sign in with Google
            </button>
          </div>

          <p className="mt-10 text-center text-sm text-gray-500">
            Not a member?{' '}
            <Link to="/signup" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
            Sign Up, Today!
            </Link>
          </p>
        </div>
      </div>
    </>
  )
}

export default SignInPage
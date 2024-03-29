import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { register, googleSignIn } from '../config/firebase-config';
import { useNavigate  } from 'react-router-dom';
const SignUpPage = () => {
  // Hook for navigation
  const navigate = useNavigate();
  
  // State for managing sign-up messages
  const [loginMessage, setLoginMessage] = useState({ text: '', isError: false });

  // Handles sign-up with Google
  const handleGoogleSignUp = async () => {
      if (await googleSignIn() === true) {
        console.log("signup successful");
        navigate('/explore');
      } else {
        console.log("signup failed");
      }
  }
  
  // Handles form submission for regular sign-up
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Extract user data from the form
    const formData = new FormData(event.target);
    const userData = {
      name: formData.get('name'),
      email: formData.get('email'),
      password: formData.get('password'), 
      passwordConfirmation: formData.get('password-confirmation'),
    };

    console.log('User sign-up data:', userData);

    // Attempt to register the user with provided data
    if(await register(userData) === true){
      console.log("Register successful");
      navigate('/explore');
    } else {
      console.log("Register failed");
      setLoginMessage({ text: 'Sign up failed. Account Already Exist, Please SignIn or Choose a Diffrent Email.', isError: true });
    }
  };

  return (
    <>
      <div className="mt-24 flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-20 w-auto"
            src="https://storage.googleapis.com/tmp-bucket-json-data/Logo.svg"
            alt="Your Company"
          />
          <h2 className="mt-6 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
            Sign up to create an account
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>
            <div>
              <label htmlFor="name" className="block text-sm font-medium leading-6 text-gray-900">
                Name
              </label>
              <div className="mt-2">
                <input
                  id="name"
                  name="name"
                  type="text"
                  autoComplete="name"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-pink-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

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
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
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
                Sign up
              </button>
            </div>

            <div style={{ paddingTop: '0px' }}>
            <button
              type="button"
              onClick={handleGoogleSignUp}
              className="flex items-center justify-center w-full rounded-md bg-white border border-gray-300 px-3 py-1.5 text-sm font-semibold leading-6 text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:border-pink-500 focus:ring-2 focus:ring-offset-2 focus:ring-pink-500"
            >
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/768px-Google_%22G%22_logo.svg.png"
                alt="Google Logo"
                className="h-5 w-5 mr-2"
              />
              Sign up with Google
            </button>
          </div>

          </form>
          {loginMessage.text && (
          <div
            className={`mt-3 mx-auto max-w-sm text-center ${loginMessage.isError ? 'text-red-700 bg-red-100' : 'text-green-700 bg-green-100'} p-4 rounded-lg mb-4`}
            role="alert"
          >
            {loginMessage.text}
          </div>
        )}
          <p className="mt-10 text-center text-sm text-gray-500">
            Returning Member?{' '}
            <Link to="/signin" className="font-semibold leading-6 text-pink-600 hover:text-pink-500">
              Sign In, Today!
            </Link>
          </p>
        </div>
      </div>
    </>
  );
};

export default SignUpPage;

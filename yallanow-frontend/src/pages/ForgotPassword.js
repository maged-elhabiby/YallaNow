import React, { useState } from 'react';
import {resetPassword} from '../firebase-config';

function ForgotPassword() {
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log({ email })

        try {
            await resetPassword(email);
            setMessage('Password reset email sent!');
            setEmail('');
        } catch (error) {
            console.error('Error sending password reset email:', error);
            setMessage('Failed to send password reset email.');
        }
    };

    const handleBack = () => {
        window.history.back();
    }

    return (
        <div className="mt-24 flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
            <div style={{paddingBottom: '10px'}}>
            <button onClick={handleBack} className="text-sm font-semibold text-indigo-600 hover:text-indigo-500">
                  {'<'} Return to Sign In Page
            </button>
            </div>
            <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                    <img
                    className="mx-auto h-20 w-auto"
                    src="https://storage.googleapis.com/tmp-bucket-json-data/Logo.svg"
                    alt="YallaNow"
                />
                <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
                    Forgot Password
                </h2>
            </div>

            <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form className="space-y-6" action="#" onSubmit={handleSubmit} method="POST">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                            Email address
                        </label>
                        <label htmlFor="email" className="block text-xs font-small leading-6 text-gray-900">
                            Enter your email and we will send you a password reset link.
                        </label>
                        <div className="mt-2">
                            <input
                                id="email"
                                name="email"
                                type="email"
                                autoComplete="email"
                                required
                                value={email}
                                onChange={handleEmailChange}
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <button
                            type="submit"
                            className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            Reset Password
                        </button>
                    </div>
                </form>
                <p className="mt-3 text-sm text-gray-900">{message}</p>
            </div>
        </div>
    );
}

export default ForgotPassword;

import React from 'react';
import ReactDOM from 'react-dom/client'; // Correctly imported for React 18
import './index.css';
import App from './App';
import {BrowserRouter} from 'react-router-dom';
import reportWebVitals from './reportWebVitals';
import {AuthProvider} from './AuthContext';

// Create a root
const root = ReactDOM.createRoot(document.getElementById('root'));

// Use the root to render the app, React 18 style
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <App/>
            </AuthProvider>
        </BrowserRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

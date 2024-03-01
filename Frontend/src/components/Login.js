//this is the JS for a login page
import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../App.css';

const Login = () => {

    return (
        <div className="login">
            <h1>Login</h1>
            <form>
                <input type="text" placeholder="Username" />
                <input type="password" placeholder="Password" />
                <button type="submit">Login</button>
            </form>
            <Link to="/signup">Don't have an account? Sign up here</Link>
        </div>
    );
}

export default Login;
//this is the JS for a login page
import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import { Dropdown } from 'flowbite-react';

import '../App.css';

const Login = () => {

    return (
        <div>
            <Dropdown label="Dropdown button" dismissOnClick={false}>
                <Dropdown.Item>Dashboard</Dropdown.Item>
                <Dropdown.Item>Settings</Dropdown.Item>
                <Dropdown.Item>Earnings</Dropdown.Item>
                <Dropdown.Item>Sign out</Dropdown.Item>
            </Dropdown>
        </div>
    );
}

export default Login;
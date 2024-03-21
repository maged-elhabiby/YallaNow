import React, { useState } from 'react';
import './login.css';
import { Dropdown } from 'flowbite-react';
import Typography from '@mui/material/Typography';
import { useNavigate } from 'react-router-dom';

function LoginForm() {
  const [loginData, setLoginData] = useState({ email: '', password: '' });
  const [signupData, setSignupData] = useState({ name: '', email: '', password: '', acceptedTerms: false });

  const handleLoginClick = () => {
    document.querySelector(".wrapper").classList.add("active");
  };

  const handleSignupClick = () => {
    document.querySelector(".wrapper").classList.remove("active");
  };

    const handleLoginSubmit = async (e) => { // HANDLE API CALL HERE FOR LOGIN
        e.preventDefault();
        
        try {
          const response = await fetch('/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
          });
          console.log('LOGIN DATA');
          console.log(loginData);

          const data = await response.json();
          
          console.log(data);
        } catch (error) {
          console.error('Login error:', error);
        }
      };
      
      const handleSignupSubmit = async (e) => { // HANDLE API CALL HERE FOR SIGNUP
        e.preventDefault();
      
        try {
          const response = await fetch('/signup', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(signupData), 
          });
          console.log('SIGNUP DATA');
          console.log(signupData);
          const data = await response.json();
          console.log(data);
        } catch (error) {
          console.error('Signup error:', error);
        }
      };

  const handleLoginInputChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
  };

  const handleSignupInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    const val = type === 'checkbox' ? checked : value;
    setSignupData({ ...signupData, [name]: val });
  };

  const navigate = useNavigate();



  return (
    <section className="container" >
      <div className="navigation">
        {/* <Typography
          variant="h6"
          noWrap
          component="a"
          href="#app-bar-with-responsive-menu"
          sx={{
            mr: 2,
            display: { xs: 'none', md: 'flex' },
            fontFamily: 'monospace',
            fontWeight: 700,
            letterSpacing: '.3rem',
            color: 'inherit',
            textDecoration: 'none',
          }}
        >
          <img src="https://i.ibb.co/L6vFwcd/overall.png" alt="Avatar" style={{ maxWidth: '100px', height: '50px' }} />
        </Typography> */}
        <Dropdown label="Menu" dismissOnClick={false} class="bg-blue-400 hover:bg-blue-500 text-white py-1 font-semibold px-4 rounded">
          <Dropdown.Item>Dashboard</Dropdown.Item>
          <Dropdown.Item>Settings</Dropdown.Item>
          <Dropdown.Item>Earnings</Dropdown.Item>
          <Dropdown.Item>Sign out</Dropdown.Item>
        </Dropdown> 
        <p class="font-serif font-bold text-5xl italic text-blue-400 mr-40 hover:text-blue-500" style={{ cursor: 'pointer'}} onClick = {() => navigate('/')}>YallaNow</p>

        <div></div>
      </div>

      <section className="container2">
        <section className="wrapper">
          <div className="form signup">
            <header onClick={handleSignupClick}>Signup</header>
            <form onSubmit={handleSignupSubmit}>
              <input type="text" name="name" placeholder="Full name" value={signupData.name} onChange={handleSignupInputChange} required />
              <input type="text" name="email" placeholder="Email address" value={signupData.email} onChange={handleSignupInputChange} required />
              <input type="password" name="password" placeholder="Password" value={signupData.password} onChange={handleSignupInputChange} required />
              <div className="checkbox">
                <input type="checkbox" id="signupCheck" name="acceptedTerms" checked={signupData.acceptedTerms} onChange={handleSignupInputChange} />
                <label htmlFor="signupCheck">I like events c:</label>
              </div>
              <input type="submit" value="Signup" />
            </form>
          </div>

          <div className="form login">
            <header onClick={handleLoginClick}>Login</header>
            <form onSubmit={handleLoginSubmit}>
              <input type="text" name="email" placeholder="Email address" value={loginData.email} onChange={handleLoginInputChange} required />
              <input type="password" name="password" placeholder="Password" value={loginData.password} onChange={handleLoginInputChange} required />
              <a href="#">Forgot password?</a>
              <input type="submit" value="Login" />
            </form>
          </div>
        </section>
      </section>
    </section>
  );
}

export default LoginForm;
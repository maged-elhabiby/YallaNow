import React, { useState } from 'react';
import './login.css';
import { Dropdown } from 'flowbite-react';
import Typography from '@mui/material/Typography';

// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword , getIdToken} from "firebase/auth";
import{getDatabase, ref, set} from "firebase/database";

function LoginForm() {
  const [loginData, setLoginData] = useState({ email: '', password: '' });
  const [signupData, setSignupData] = useState({ name: '', email: '', password: '', acceptedTerms: false });
  const apifirebase = process.env.FIREBASE_API
  console.log(apifirebase);

  // Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  const firebaseConfig = {
    apiKey: "AIzaSyAFU_zSpZWKznKiEiNqrNwsi-8JIr5XfhQ",
    authDomain: "yallanow12.firebaseapp.com",
    projectId: "yallanow12",
    storageBucket: "yallanow12.appspot.com",
    messagingSenderId: "463351798443",
    appId: "1:463351798443:web:dc83ad7b3ebbf1e88f75ee",
    measurementId: "G-77DNY9TMVN"
  };

  // Initialize Firebase
  const app = initializeApp(firebaseConfig);
  const analytics = getAnalytics(app);
  const auth = getAuth();
  const database = getDatabase();

  const Register = async (signup) => {
    console.log("we are in register");
    const { email, password } = signup;

      const userCredential = await createUserWithEmailAndPassword(auth, email, password)
      .then((userCredential) => {
        // Signed in 
        console.log(userCredential);
        const user = userCredential.user;
        // ...
      })
      .catch((error) => {
        const errorCode = error.code;
        const errorMessage = error.message;
        // ..
      });

      console.log("we are exiting register")
  }


  const Login = async (login) => {
    console.log("we are in login");
    const { email, password } = login;
    await signInWithEmailAndPassword(auth, email, password)
    .then((userCredential) => {
      // Signed in 
      console.log(userCredential)
      const user = userCredential.user;
      const idToken =  getIdToken(user);
      console.log(idToken);
      // ...
    })
    .catch((error) => {
      const errorCode = error.code;
      const errorMessage = error.message;
    });
    console.log("we are exiting login");
  }







  const handleLoginClick = () => {
    document.querySelector(".wrapper").classList.add("active");
  };

  const handleSignupClick = () => {
    document.querySelector(".wrapper").classList.remove("active");
  };

  const handleLoginSubmit = async (e) => { // HANDLE API CALL HERE FOR LOGIN
    e.preventDefault();

    try {
      console.log('LOGIN DATA');
      console.log(loginData);
      await Login(loginData);

    } catch (error) {
      console.error('Login error:', error);
    }
  };

  const handleSignupSubmit = async (e) => { // HANDLE API CALL HERE FOR SIGNUP
    e.preventDefault();

    try {
      
      console.log('SIGNUP DATA');
      console.log(signupData);
     
      await Register(signupData);
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

  return (
    <section className="container">
      <div className="navigation">
        <Typography
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
        </Typography>
        <Dropdown label="Menu" dismissOnClick={false} class="hover:bg-blue-600 text-white font-semibold py-1 px-2 rounded">
          <Dropdown.Item>Dashboard</Dropdown.Item>
          <Dropdown.Item>Settings</Dropdown.Item>
          <Dropdown.Item>Earnings</Dropdown.Item>
          <Dropdown.Item>Sign out</Dropdown.Item>
        </Dropdown>
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
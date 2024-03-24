import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import StarterAppBar from './StarterNav.js';
import {
  AppBar,
  Typography,
  Container,
  Button,
} from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    height:"100vh",
    
  },
  appBar: {
    backgroundColor: '#FFFFFF',
    position: 'sticky',
    top: 0,
    height: 64,
  },
  title: {
    flexGrow: 1,
  },
  hero: {
    background: `url('https://res.cloudinary.com/dt8r2amry/image/upload/v1711150824/pexels-helena-lopes-745045_zi5wk3.jpg')`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    height: '89vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#e6f2ff',
    textShadow: '2px 2px 4px rgba(0, 0, 0, 0.5)',
    position: 'relative',
    transition: 'background-position 0.3s ease-out',
  },
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  contentContainer: {
    zIndex: 1,
    textAlign: 'center',
  },
  buttonContainer: {
    marginTop: '20px',
    textAlign: 'center',
  },
  bigButton: {
    width: '20%',
    fontSize: '15px',
    padding: '20px',
    color: "#e6f2ff",
    border: `3px solid #e6f2ff`,
  },
}));

const LandingPage = () => {
  const classes = useStyles();
  const navigate = useNavigate();

  return (
    <div className={[classes.root, {overFlow:"hidden"}]}>

        <StarterAppBar />

      <div className={`${classes.hero}`}>
        <div className={classes.overlay}/>
        <Container className={classes.contentContainer}>
          <Typography variant="h2" align="center" gutterBottom>
            Welcome to YallaNow!
          </Typography>
          <Typography variant="h5" align="center" paragraph>
            Plan and organize your events with ease
          </Typography>
          <Button variant="outlined" color="#e6f2ff" className={classes.bigButton} onClick={() => navigate('/MainPage')} >
            Get Started
          </Button>
        </Container>
      </div>
      <footer style={{ backgroundColor: '#333', color: '#fff', padding: '8.5px 0', textAlign: 'center' }}>
        <Typography variant="body2">
          &copy; {new Date().getFullYear()} YallaNow. All rights reserved.
        </Typography>
      </footer>
    </div>
  );
};

export default LandingPage;

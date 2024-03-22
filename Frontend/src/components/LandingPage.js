import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import Nav from './nav.js';
import { Carousel } from 'flowbite-react';
import {
  AppBar,
  Typography,
  Container,
  Button,
  Grid,
  Card,
  CardContent,
} from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  appBar: {
    backgroundColor: '#FFFFFF',
    position: 'sticky',
    top: 0,
    height: 81,
  },
  title: {
    flexGrow: 1,
  },
  hero: {
    background: `url('https://as1.ftcdn.net/v2/jpg/01/16/23/34/1000_F_116233476_rpqth2jU79SC0ioUtz4pZDAXloeFHWYy.jpg')`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    height: '80vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#fff',
  },
  card: {
    backgroundColor: theme.palette.primary.main,
    color: '#fff',
    minHeight: '200px',
  },
  carouselContainer: {
    marginTop: '50px',
  },
}));

const LandingPage = () => {
  const classes = useStyles();
  const navigate = useNavigate();

  return (
    <div className={classes.root}>
      <AppBar position="static" className={classes.appBar}>
        <Nav />
      </AppBar>
      <div className={classes.hero}>
        <Container>
          <Typography variant="h2" align="center" gutterBottom>
            Welcome to YallaNow
          </Typography>
          <Typography variant="h5" align="center" paragraph>
            Plan and organize your events with ease
          </Typography>
          <Button variant="contained" color="primary" onClick={() => navigate('/MainPage')}>
            Get Started
          </Button>
        </Container>
      </div>
      <Container>
        <Grid container spacing={3} justify="center" style={{ marginTop: '50px' }}>
          <Grid item xs={12} md={4}>
            <Card className={classes.card}>
              <CardContent>
                <Typography variant="h5" gutterBottom>
                  Easy Planning
                </Typography>
                <Typography variant="body1">
                  Plan your events effortlessly with our intuitive interface.
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card className={classes.card}>
              <CardContent>
                <Typography variant="h5" gutterBottom>
                  Collaborative
                </Typography>
                <Typography variant="body1">
                  Collaborate with team members and make planning seamless.
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card className={classes.card}>
              <CardContent>
                <Typography variant="h5" gutterBottom>
                  Customizable
                </Typography>
                <Typography variant="body1">
                  Customize your event details and make them unique.
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
      <footer style={{ backgroundColor: '#333', color: '#fff', padding: '20px 0', textAlign: 'center' }}>
        <Typography variant="body2">
          &copy; {new Date().getFullYear()} EventApp. All rights reserved.
        </Typography>
      </footer>
    </div>
  );
};

export default LandingPage;

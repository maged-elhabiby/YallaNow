import React from 'react';
import { useNavigate } from 'react-router-dom';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import AdbIcon from '@mui/icons-material/Adb'; // This can be replaced with your logo icon

function StarterAppBar() {
  const navigate = useNavigate();

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <AdbIcon sx={{ display: 'flex', mr: 1, cursor: 'pointer' }} onClick={() => navigate('/')} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            onClick={(e) => {
                e.preventDefault();
                navigate('/');
            }}
            href="#"
            sx={{
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
              cursor: 'pointer',
            }}
          >
            YALLANOW
          </Typography>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default StarterAppBar;

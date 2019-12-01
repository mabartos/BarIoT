import React from 'react';
import clsx from 'clsx';
import { Switch, Route,Link, BrowserRouter } from "react-router-dom";
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import Badge from '@material-ui/core/Badge';
import Container from '@material-ui/core/Container';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import NotificationsIcon from '@material-ui/icons/Notifications';
import Grid from '@material-ui/core/Grid';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import DashboardIcon from '@material-ui/icons/Dashboard';
import HomeIcon from '@material-ui/icons/Home';
import GeneralTile from '../GeneralTile';
import AddTile from '../AddTile';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import HouseIcon from '@material-ui/icons/House';
import Collapse from '@material-ui/core/Collapse';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import UserProfile from '../UserProfile.js'
import MySwitch from '../MySwitch'



export function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        BartIoT
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

const drawerWidth = 240;

export const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto',
  },
  container: {
    minWidth : 600,
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 240,
  },
  blueGradientContent: {
    width: '100%'
  },
  nested: {
    paddingLeft: theme.spacing(4)
  },
  
}));


export default function Dashboard() {
  const classes = useStyles();
  
  const [open, setOpen] = React.useState(false);
  const [open_h, setOpen_h] = React.useState(true);
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };
  const handleClick = () => {
    setOpen_h(!open_h);
  };
  const value = 25;
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(classes.menuButton, open && classes.menuButtonHidden)}
          >
            <MenuIcon />
          </IconButton>
          <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
            Username
          </Typography>
          <IconButton color="inherit">
            <Badge badgeContent={4} color="secondary">
              <NotificationsIcon />
            </Badge>
          </IconButton>
        </Toolbar>
      </AppBar>
       <Drawer
        variant="permanent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
          <ListItem 
          component={Link} to={"/dashboard"}
          button 
          >
            <ListItemIcon>
              <DashboardIcon />
            </ListItemIcon>
            <ListItemText primary={"Dashboard"} />
          </ListItem>

          <ListItem button onClick={handleClick}>
            <ListItemIcon>
              <HouseIcon />
            </ListItemIcon>
            <ListItemText primary="Homes" />
            {open_h ? <ExpandLess /> : <ExpandMore />}
          </ListItem>
        <Collapse in={open_h} timeout="auto" unmountOnExit>
        <List component="div" disablePadding>
          <ListItem
            component={Link} to={"/dashboard/Home_1"}
           button className={classes.nested} button
          >
            <ListItemIcon>
              <HomeIcon />
            </ListItemIcon>
            <ListItemText primary="Home_1" />
          </ListItem>
        </List>
      </Collapse>
      <ListItem 
          component={Link} to={"/dashboard/UserProfile"}
          button
          
          >
            <ListItemIcon>
              <AccountCircleIcon />
            </ListItemIcon>
            <ListItemText primary="User Profile"/>
          </ListItem>

        <Divider />
      </Drawer>
      <div className={classes.blueGradientContent}>
        <main className={classes.content}>
          <div className={classes.appBarSpacer} />
          <Switch>
            <Route exact path="/dashboard" render={() => 
              <div>
                <Container maxWidth="lg" className={classes.container}>
                  <Grid container spacing={3}>
                    <GeneralTile name={"Home_1"} imageName="flat" type="home" link="/dashboard/Home_1"/>
                    <GeneralTile name={"Home_2"} imageName="camper_van" type="home"/>
                    <GeneralTile name={"Home_3"} imageName="other" type="home"/>
                    <GeneralTile name={"Home_4"} imageName="house" type="home"/>
                    <AddTile name={"Home"}/>
                  </Grid>
                </Container>
              </div>} 
            />
            <Route path="/dashboard/Home_1" render={() => 
            <div>
              <Container maxWidth="lg" className={classes.container}>
                <Grid container spacing={3}>
                  <GeneralTile name={"Room_1"} imageName="kitchen" type="room" link="/dashboard/Home_1_Room_1"/>
                  <GeneralTile name={"Room_1"} imageName="garage" type="room"/>
                  <GeneralTile name={"Room_2"} imageName="room" type="room"/>
                  <GeneralTile name={"Room_3"} imageName="bathroom" type="room"/>
                  <GeneralTile name={"Room_4"} imageName="other" type="room"/>
                  <GeneralTile name={"Room_4"} imageName="livingroom" type="room"/>
                  <AddTile name={"Room"}/>
                </Grid>
              </Container>
            </div>} />
            <Route path="/dashboard/UserProfile">
              <UserProfile/>
            </Route>
            <Route path="/dashboard/test" render={() => 
              <div><MySwitch/></div>} />
              
            
            <Route path="/dashboard/Home_1_Room_1">
            <div>
              <Container maxWidth="lg" className={classes.container}>
                <Grid container spacing={3}>
                  <GeneralTile name={"Temp"} imageName="livingroom" type="device" devtype="temp"/>
                  <GeneralTile name={"Humidity"} imageName="livingroom" type="device" devtype="hum"/>
                  <GeneralTile name={"Light"} imageName="livingroom" type="device" devtype="light"/>
                  <GeneralTile name={"Socket"} imageName="livingroom" type="device" devtype="socket"/>
                  <GeneralTile name={"Air Conditioner"} imageName="livingroom" type="device" devtype="ac"/>
                  <GeneralTile name={"Heater"} imageName="livingroom" type="device" devtype="heater"/>
                  <GeneralTile name={"Power Stats"} imageName="livingroom" type="device" devtype="stats"/>
                  <AddTile name={"Device"}/>
                </Grid>
              </Container>
            </div>
            </Route>
          </Switch>
          
          <Copyright />
        </main>
      </div>

    </div>
  );
}

//Authors Design: Marek Lorinc <xlorin00> Martin Bartos <xbarto96>
//Communication with backend : Maximilian Kosiarcik <xkosia00>

import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import Avatar from '@material-ui/core/Avatar';
import AvatarImage from '../assets/avatar_home.png'
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import Button from '@material-ui/core/Button';
import DeleteIcon from '@material-ui/icons/Delete';
import Tooltip from '@material-ui/core/Tooltip';
import Snackbar from '@material-ui/core/Snackbar';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import { amber, green } from '@material-ui/core/colors';
import SnackbarContent from '@material-ui/core/SnackbarContent';
import clsx from 'clsx';
import CloseIcon from '@material-ui/icons/Close';
import PropTypes from 'prop-types';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Link } from "react-router-dom";
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import Paper from '@material-ui/core/Paper';
import ProgressBar from './ProgressBar'
import TileLight from './TileLight'
import TileSocket from './TileSocket'
import TileAC from './TileAC'
import TileHeater from './TileHeater'
import TileStats from './TileStats'
import axios from 'axios';

const useStyles = makeStyles(theme => ({

    container: {
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
        height: 240,
    },
    card: {
        minWidth: 290,
        maxWidth: 345,
        background: 'rgba(26,41,128,0.7)',
        color: 'white',
    },
    cardTitle: {
        color: 'white',
        fontSize: 'large'
    },
    root: {
      minHeight: 230,
      height:250,
      paddingTop: "30px", // 16:9
      backgroundSize: 'cover',
  },
    media: {
        minHeight: 250,
        paddingTop: '56.25%', // 16:9
        backgroundSize: 'cover',
    },
    avatar: {
        backgroundImage: `url(${AvatarImage})`,
        backgroundSize: 'cover',
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
    },
    paper: {
      padding: theme.spacing(1),
      textAlign: 'center',
      color: 'black',
  },
}));

const variantIcon = {
    success: CheckCircleIcon
  };

  const useStyles1 = makeStyles(theme => ({
    success: {
      backgroundColor: green[600],
    },
    error: {
      backgroundColor: theme.palette.error.dark,
    },
    info: {
      backgroundColor: theme.palette.primary.main,
    },
    warning: {
      backgroundColor: amber[700],
    },
    icon: {
      fontSize: 20,
    },
    iconVariant: {
      opacity: 0.9,
      marginRight: theme.spacing(1),
    },
    message: {
      display: 'flex',
      alignItems: 'center',
    },
  }));

function MySnackbarContentWrapper(props) {
    const classes = useStyles1();
    const { className, message, onClose, variant, ...other } = props;
    const Icon = variantIcon[variant];

    return (
      <SnackbarContent
        className={clsx(classes[variant], className)}
        aria-describedby="client-snackbar"
        message={
          <span id="client-snackbar" className={classes.message}>
            <Icon className={clsx(classes.icon, classes.iconVariant)} />
            {message}
          </span>
        }
        action={[
          <IconButton key="close" aria-label="close" color="inherit" onClick={onClose}>
            <CloseIcon className={classes.icon} />
          </IconButton>,
        ]}
        {...other}
      />
    );
  }

  MySnackbarContentWrapper.propTypes = {
    className: PropTypes.string,
    message: PropTypes.string,
    onClose: PropTypes.func,
    variant: PropTypes.oneOf(['error', 'info', 'success', 'warning']).isRequired,
  };

export default function GeneralTile(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [dial_open, dial_setOpen] = React.useState(false);
    const handleClick = () => {
        setOpen(true);
        dial_setOpen(false);
        //delete the object
        axios.delete(props.url).then(response => {
            props.refresh();
        });
    };
    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
        setOpen(false);
    };

    const handleClickOpen = () => {
        dial_setOpen(true);
      };

      const handleClickClose = () => {
        dial_setOpen(false);
      };


    const click=(e)=>{
       //TODO redirect ??
    }
    const value = 30;
    const progbarval= value -16;
    const humidity = 66;
    var bg=require(`../assets/${props.imageName}`)
    return (
        <Grid item xs={12} md={4} spacing={2}>
            <Card button className={classes.card} >
                <CardHeader
                    classes={{ title: classes.cardTitle }}
                    avatar={
                        <Avatar aria-label="recipe" className={classes.avatar}>
                        </Avatar>
                    }
                    title={props.name}
                    action={
                        <div>
                        <Tooltip title="Delete">
                            <IconButton onClick={handleClickOpen} aria-label="delete">
                            <DeleteIcon />
                            </IconButton>

                        </Tooltip>
                        <Dialog
                            open={dial_open}
                            onClose={handleClickClose}
                            aria-labelledby="alert-dialog-title"
                            aria-describedby="alert-dialog-description"
                        >
                            <DialogTitle id="alert-dialog-title">{"Delete " + props.type + "?"}</DialogTitle>
                            <DialogContent>
                            <DialogContentText id="alert-dialog-description">
                                Are you sure that you want to delete this {props.type}?
                            </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                            <Button onClick={handleClickClose} color="primary">
                                No
                            </Button>
                            <Button onClick={handleClick} color="primary" autoFocus>
                                Yes
                            </Button>
                            </DialogActions>
                        </Dialog>


                        <Snackbar
                        anchorOrigin={{
                          vertical: 'bottom',
                          horizontal: 'left',
                        }}
                        open={open}
                        autoHideDuration={6000}
                        onClose={handleClose}
                      >
                        <MySnackbarContentWrapper
                          onClose={handleClose}
                          variant="success"
                          message= {props.type + " succesfully deleted"}
                        />
                      </Snackbar>
                      </div>
                      }
                />




                {(() => {
                    if(props.type === 'device'){
                      switch(props.devtype){
                        case 'temperature':
                        case 'humidity' :
                          return(
                            <ProgressBar className={classes.root} type={props.devtype}/>
                         )
                        case 'light':
                          return(
                            <div>
                              <CardMedia
                                className={classes.root}
                                style={{background:'white'}}
                              >
                                <TileLight/>
                              </CardMedia>
                            </div>


                          )
                        case 'socket':
                          return(
                            <div>
                              <CardMedia
                                className={classes.root}
                                style={{background:'white'}}
                              >
                                <TileSocket/>
                              </CardMedia>
                            </div>
                          )
                        case 'airconditioner':			
                          return(
                            <div>
                              <CardMedia
                                className={classes.root}
                                style={{background:'white'}}
                              >
                              <TileAC/>
                              </CardMedia>
                            </div>
                          )
                        case 'heater':
                          return(
                            <div>
                              <CardMedia
                                className={classes.root}
                                style={{background:'white'}}
                              >
                              <TileHeater/>
                              </CardMedia>
                            </div>
                          )
                        case 'stats':
                          return(
                            <div>
                              <CardMedia
                                className={classes.root}
                                style={{background:'white'}}
                              >
                              <TileStats/>
                              </CardMedia>
                            </div>
                          )

                      }


                    }
                    else{
                      return(
                        <CardMedia
                          component={Link} to={props.link}
                          className={classes.media}
                          style={{backgroundImage:`url(${bg})`,
                          backgroundPosition: "center",
                          backgroundRepeat: "no-repeat",
                          backgroundSize: "cover"
                       }}
                          onClick={click}
                        />
                      )
                    }
                })()}
            </Card>
        </Grid>
    );
}

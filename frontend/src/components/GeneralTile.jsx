import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import Avatar from '@material-ui/core/Avatar';
import BackgroundImage from '../assets/smart.jpg'
import AvatarImage from '../assets/avatar_home.png'
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import MoreHoriIcon from '@material-ui/icons/MoreHoriz';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles(theme => ({

    container: {
        display: 'flex',
        overflow: 'auto',
        flexDirection: 'column',
        height: 240,
    },
    card: {
        maxWidth: 345,
        background: 'rgba(26,41,128,0.5)',
        color: 'white',
    },
    cardTitle: {
        color: 'white',
        fontSize: 'large'
    },
    media: {
        height: 0,
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
}));

export default function GeneralTile(props) {
    const classes = useStyles();

    const click=(e)=>{
       //TODO redirect ??
    }

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
                        <IconButton aria-label="settings">
                          <MoreHoriIcon color='secondary' />
                        </IconButton>
                      }
                />
                <CardMedia
                    className={classes.media}
                    style={{backgroundImage:`url(${BackgroundImage})`}}
                    onClick={click}
                />
            </Card>
        </Grid>
    );
}
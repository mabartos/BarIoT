import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import Avatar from '@material-ui/core/Avatar';
import AvatarImage from '../assets/add.png'
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import SimpleSelect from './SimpleSelect'
import axios from 'axios';



const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(1),
        textAlign: 'center',
        color: 'black',
    },
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

class MyForm extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        name: '',
        image: '',
        type: '',
      };
    }
    mySubmitHandler = (event) => {
      event.preventDefault();
      const data = {
        name: this.state.name,
      };
      if (this.state.image) {
        data.image = this.state.image;
      }
      if (this.state.type) {
        data.type = this.state.type;
      }
      alert(data.type);
      axios.post( this.props.url, data).then(response => {
          this.props.refresh();
      });

    }
    myChangeHandler = (event) => {
      this.setState({name: event.target.value});
    }

    selectChanged = (val) => {
        let image = null;
        let type = null;
        switch(val) {
            case 'Flat':
                image = 'flat.jpg';
                break;
            case 'Camper van':
                image = 'camper_van.jpg';
                break;
            case 'House':
                image = 'house.jpg';
                break;
            case 'Other':
                image = 'other.jpg';
                break;
            case 'Kitchen':
                image = 'kitchen.jpg';
                break;
            case 'Bathroom':
                image = 'bathroom.jpg';
                break;
            case 'Living room':
                image = 'livingroom.jpg';
                break;
            case 'Garage':
                image = 'garage.jpg';
                break;
            case 'Bed Room':
                image = 'bedroom.png';
                break;
            // for devices as they don't have images
            case 'Humidity sensor':
                type = 'humidity';
                break;
            case 'Temperature sensor':
                type = 'temperature';
                break;
            case 'Light':
                type = 'light';
                break;
            case 'Heater':
                type = 'heater';
                break;
            case 'Air conditioner':
                type = 'ac';
                break;
            case 'Socket':
                type = 'socket';
                break;
            case 'Stats':
                type = 'stats';
                break;

        }
        this.setState({
            image: image,
            type: type,
            })
    }

    input_style = {
        height: "50px",
        width:"300px",
        fontSize: "16pt"
    };
    render() {
      return (

        <Grid container spacing={2}>
            <Grid item xs={12} sm={12}>
            <TextField
                  autoComplete="fname"
                  name= {this.props.name + " name"}
                  variant="outlined"
                  required
                  fullWidth
                  id="homeName"
                  label={this.props.name + " name"}
                  autoFocus
                  onChange={this.myChangeHandler}
                />
            </Grid>
            <SimpleSelect name = {this.props.name} selectChanged={this.selectChanged}/>
            <Grid item xs={12} sm={12}>
            <Button fullWidth variant="contained" color="primary" onClick={this.mySubmitHandler}>
                {"Add " + this.props.name}
            </Button>
            </Grid>
        </Grid>


      );
    }
  }




export default function AddTile(props) {
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
                    title={"Add new " + props.name}

                />
                <CardMedia className={classes.root}>

                    <Grid item xs={12}>
                    <Paper className={classes.paper}>
                      <MyForm name={props.name} url={props.url} refresh={props.refresh}/>
                    </Paper>
                    </Grid>


                </CardMedia>
            </Card>
        </Grid>
    );
}
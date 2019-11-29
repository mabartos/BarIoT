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
      this.state = { homename: '' };
    }
    mySubmitHandler = (event) => {
      event.preventDefault();
      alert(this.props.name + " " + this.state.homename + " added");
    }
    myChangeHandler = (event) => {
      this.setState({homename: event.target.value});
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
            <SimpleSelect name = {this.props.name}/>
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
                      <MyForm name={props.name}/>  
                    </Paper>
                    </Grid>
                    
                
                </CardMedia>
            </Card>
        </Grid>
    );
}
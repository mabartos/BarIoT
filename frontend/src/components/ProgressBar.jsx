//Tile for write temperature and humidity in progress bar
//Authors Design: Marek Lorinc <xlorin00>
//	Communication with backend : Maximilian Kosiarcik <xkosia00>
import React from 'react';
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import CardMedia from '@material-ui/core/CardMedia';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({

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
      maxHeight: 0,
      paddingTop: '56.25%', // 16:9
      backgroundSize: 'cover',
  },
  paper: {
    padding: theme.spacing(1),
    textAlign: 'center',
    color: 'black',
},
}));

export default function ProgressBar(props) {
    const classes = useStyles();
    
    const value = 22;
    const progbarval= value -16;
    const humidity = 66;
    return (
      <Paper className={classes.paper}>
        <center>
          <Grid item xs={8} >                        
            <CardMedia className={classes.root}>
              {(() => {
                if(props.type === 'temp'){
                  return(                  
                    <CircularProgressbar value={value} minValue= {10} maxValue={40} text={`${value}°C`} 
                      styles={{
                        path: {
                          // Path color
                          stroke: `rgb(${progbarval*12}, ${(255-progbarval*8)}, 40)`,
                          // Whether to use rounded or flat corners on the ends - can use 'butt' or 'round'                      
                        },
                        // Customize the text
                        text: {
                          // Text color
                          fill: `rgb(${progbarval*12}, ${(255-progbarval*8)}, 0)`,
                          // Text size
                          fontSize: '21px',
                        },
                      }}
                    />
                  )                  
                }
                else{
                  return(
                    <CircularProgressbar value={humidity} text={`${humidity}%`} />
                  )
                } 
            })()}
            </CardMedia>
          </Grid>
        </center>
      </Paper>
  );
}

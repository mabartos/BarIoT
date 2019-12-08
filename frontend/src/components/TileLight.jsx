//Device tile Light
//Authors : Marek Lorinc <xlorin00>
import React from 'react';
import PropTypes from 'prop-types';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Slider from '@material-ui/core/Slider';
import Typography from '@material-ui/core/Typography';
import Tooltip from '@material-ui/core/Tooltip';
import Box from '@material-ui/core/Box';
import LightBulb from '../assets/light_bulb.png'
import Grid from '@material-ui/core/Grid';
import { updateExpression } from '@babel/types';

const useStyles = makeStyles(theme => ({
  root: {
    width: 70 + theme.spacing(3) * 2,
  },
  margin: {
    height: theme.spacing(3),
  },
}));

var imgint = 0.9;
const PrettoSlider = withStyles({
  root: {
    color: '#ffcc00',
    height: 8,
  },
  thumb: {
    height: 24,
    width: 24,
    backgroundColor: '#fff',
    border: '2px solid currentColor',
    marginTop: -8,
    marginLeft: -12,
    '&:focus,&:hover,&$active': {
      boxShadow: 'inherit',
    },
  },
  active: {},
  valueLabel: {
    left: 'calc(-50% + 4px)',
  },
  track: {
    height: 8,
    borderRadius: 4,
  },
  rail: {
    height: 8,
    borderRadius: 4,
  },
})(Slider);



export default function TileLight() {
  const classes = useStyles();
  
  const [actopacity, setOpacity] = React.useState(0.2);

  const handleSliderChange = (event, opcty) => {
    setOpacity(opcty/100 + 0.08);
  };
  
  return (
    <Grid container spacing={3}>
    <Grid item xs={6}>
    <img src={LightBulb} alt="Logo" width = '200px'  style={{opacity: actopacity}} />

    </Grid>

    <Grid item xs={6}>
         <div className={classes.root}>
            <center>
                
              <Typography color = 'primary'>
                <font size="6">
                  <p>Intensity:</p>
                </font>           
              </Typography>
                       
            <PrettoSlider onChange={(event, val) => handleSliderChange(event, val)} valueLabelDisplay="auto" aria-label="pretto slider" defaultValue={20} />
            <div className={classes.margin} />
            </center>     
        </div>
    </Grid>
    
    </Grid>


   
  );
}
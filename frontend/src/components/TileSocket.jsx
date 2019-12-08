//Device tile socket
//Authors Marek Lorinc <xlorin00>
import React from 'react';
import PropTypes from 'prop-types';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Slider from '@material-ui/core/Slider';
import Typography from '@material-ui/core/Typography';
import Tooltip from '@material-ui/core/Tooltip';
import Box from '@material-ui/core/Box';
import MySocket from '../assets/socket.jpg'
import Grid from '@material-ui/core/Grid';
import { updateExpression } from '@babel/types';
import Switch from '@material-ui/core/Switch';
import FormGroup from '@material-ui/core/FormGroup';
import { fontSize } from '@material-ui/system';

const useStyles = makeStyles(theme => ({
  root: {
    width: 80 + theme.spacing(3) * 2,
  },
  margin: {
    height: theme.spacing(3),
  },
  
}));

const AntSwitch = withStyles(theme => ({
  root: {
    width: 70,
    height: 40,
    padding: 0,
    display: 'flex',
  },
  switchBase: {
    padding: 2,
    color: theme.palette.grey[500],
    '&$checked': {
      transform: 'translateX(29px)',
      color: theme.palette.common.white,
      '& + $track': {
        opacity: 1,
        backgroundColor: theme.palette.primary.main,
        borderColor: theme.palette.primary.main,
      },
    },
  },
  thumb: {
    width: 36,
    height: 36,
    boxShadow: 'none',
  },
  track: {
    border: `1px solid ${theme.palette.grey[500]}`,
    borderRadius: 40 / 2,
    opacity: 1,
    backgroundColor: theme.palette.common.white,
  },
  checked: {},
}))(Switch);


export default function TileLight() {
  const classes = useStyles();
  
  const [actopacity, setOpacity] = React.useState(1);
  const [power, setPower] = React.useState(500);
  const [state, setState] = React.useState({
    checkedC: true,
  });
  
  const handleChange = name => event => {
    setState({ ...state, [name]: event.target.checked });
    if(state.checkedC === true){
      setOpacity(0.1);
      setPower(0);
    }
    else{
      setPower(700);
      setOpacity(1);
    }
    
  };
  
  return (
    <Grid container spacing={3}>
    <Grid item xs={6}>
    <img src={MySocket} alt="Logo" width = '190px'  style={{opacity: actopacity}} />

    </Grid>

    <Grid item xs={6}>
         <div className={classes.root}>
            <center>
              <FormGroup>
                <Typography component="div">
                  <Grid component="label" container alignItems="center" spacing={1}>
                    <Grid item style={{color: 'black'}}>Off</Grid>
                    <Grid item >
                      <AntSwitch
                        checked={state.checkedC}
                        onChange={handleChange('checkedC')}
                        value="checkedC"
                      />
                    </Grid>
                    <Grid item xs={2} style={{color: 'black'}}>On</Grid>
                  </Grid>
                </Typography>
              </FormGroup>           
                <font size="5"  style={{color: 'black'}} align="left">Consuption:</font>

                <p><font size="6"  style={{color: `rgb(${power/12}, ${(255-power/8)}, 0)`}} >{power}W</font> </p>                   
              <div className={classes.margin} />
            </center>     
        </div>
    </Grid>
    
    </Grid>


   
  );
}
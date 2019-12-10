//Device tile stats
//Authors: Marek Lorinc <xlorin00>
import React, {Component} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import FormGroup from '@material-ui/core/FormGroup';
import { fontSize } from '@material-ui/system';
import {Bar, Line} from 'react-chartjs-2';

const useStyles = makeStyles(theme => ({
  root: {
    paddingLeft:"20px",
    width: 80 + theme.spacing(3) * 2,
  },
  margin: {
    height: theme.spacing(3),
  },
  
}));

var dates = [];
var date = new Date();

for (var i = 0; i < 10; i++){
  var tempDate = new Date();
  tempDate.setDate(date.getDate()-9+i);
  var str = tempDate.getDate() + "." + (tempDate.getMonth()+1);
  dates.push(str);  
}

class Chart extends Component{
  
  constructor(props){
    super(props);
    this.state = {

      chartData:{
        labels: dates,
        datasets:[
          {
            label:'Consumption',
            data:[
              3200,
              2550,
              4320,
              2880,
              3300,
              3560,
              3950,
              2125,
              2666,
              2950,
              3100
            ],
            backgroundColor:[
              'rgba(100,99,255,0.6)',
              
            ],
          }
        ]
      }
    }
  }
  render(){
    return(
      <div className="chart" >
        <p style={{color:"red"}}>Power consumption last 10 days(Wh)</p>
        <Line
          height = "160"
          data={this.state.chartData}
          options={{ 
            maintainAspectRatio: true,
            legend:{
              display:false
            } }}
          displayLegend = {false}
        />
      </div>
      
    
    )
  }
}



export default function TileStats() {
  const classes = useStyles();
  
  return (
    
      <div className={classes.root}>
            <center>
              <FormGroup>
                <Typography component="div">
                  <Grid component="label" container alignItems="center" spacing={1}>
                    <Chart/>
                </Grid>
                </Typography>
              </FormGroup>           
            </center>     
      </div>
   
  );
}

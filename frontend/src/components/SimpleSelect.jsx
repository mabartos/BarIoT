import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';


const useStyles = makeStyles(theme => ({
    formControl: {
      margin: theme.spacing(1),
      minWidth: 170,

    },
    selectEmpty: {
      marginTop: theme.spacing(2),
    },
    chips: {
      display: 'flex',
      flexWrap: 'wrap',
    },
    chip: {
      margin: 2,
    },
    noLabel: {
      marginTop: theme.spacing(3),
    },
  }));


  const homes = [
    'Flat',
    'Camper van',
    'House',
    'Other',
  ];
  const rooms = [
    'Kitchen',
    'Bathroom',
    'Living room',
    'Garage',
    'Bed Room',
    'Other',
  ];
  const devices = [
    'Humidity sensor',
    'Temperature sensor',
    'Light',
    'Heater',
    'Air conditioner',
    'Socket',
    'Stats',
  ];

  export default function SimpleSelect(props) {
    const classes = useStyles();
    const [age, setAge] = React.useState('');

    const inputLabel = React.useRef(null);
    const [labelWidth, setLabelWidth] = React.useState(0);
    React.useEffect(() => {
      setLabelWidth(inputLabel.current.offsetWidth);
    }, []);

    const handleChange = event => {
      setAge(event.target.value);
      props.selectChanged(event.target.value);
    };

    return (
      <div>
        <FormControl variant="outlined" className={classes.formControl}>
          <InputLabel ref={inputLabel} id="demo-simple-select-outlined-label">
            {props.name + " Type"}
          </InputLabel>
          <Select
            labelId="demo-simple-select-outlined-label"
            id="demo-simple-select-outlined"
            value={age}
            onChange={handleChange}
            labelWidth={labelWidth}

          >
                  {(() => {
                    switch(props.name){
                      case 'Home' :
                        return(homes.map(name => (
                          <MenuItem key={name} value={name}>
                            {name}
                          </MenuItem>
                        )))
                      case 'Room' :
                        return(rooms.map(name => (
                          <MenuItem key={name} value={name}>
                            {name}
                          </MenuItem>
                        )))
                      case 'Device' :
                        return(devices.map(name => (
                          <MenuItem key={name} value={name}>
                            {name}
                          </MenuItem>
                        )))

                    }
                  })()}

          </Select>
        </FormControl>
      </div>
    );
  }
// Generate devices
//Author: Maximilian Kosiarcik <xkosia00>
import React, { Component } from 'react';
import axios from 'axios';
import GeneralTile from './GeneralTile';
import Grid from '@material-ui/core/Grid';
import { Copyright } from './mainDashboard/Dashboard';
import { useStyles } from './mainDashboard/Dashboard';
import AddTile from './AddTile';

class Devices extends Component {

    state = {
        devices: null
    }


    refresh = () => {
        axios.get('/users/1/homes/'+ this.props.homeId + '/rooms/'+ this.props.roomId + '/devices').then(response => {
        this.setState({ devices: response.data })
        });
    }

    componentDidMount() {
        this.refresh();
    }

    render () {
    let devices = null;
    if (this.state.devices) {
    devices = (this.state.devices.map(device => {
            return <GeneralTile
            url={"/users/1/homes/"+device.room.home.id+"/rooms/"+device.room.id+"/devices/"+device.id}
            name={device.name} devtype={device.type} imageName="livingroom.jpg" type="device" key={device.id}
            refresh={this.refresh}/>;
    }))
    }
        return (
        <div>
        <Grid container spacing={3}>
            {devices}
            <AddTile name={"Device"} url={'/users/1/homes/'+this.props.homeId+'/rooms/' + this.props.roomId + '/devices'} refresh={this.refresh}/>
        </Grid>

         </div>
        );
    }
}

export default Devices;

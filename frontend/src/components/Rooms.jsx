import React, { Component } from 'react';
import axios from 'axios';
import GeneralTile from './GeneralTile';
import Grid from '@material-ui/core/Grid';
import { Copyright } from './mainDashboard/Dashboard';
import { useStyles } from './mainDashboard/Dashboard';
import AddTile from './AddTile';

class Rooms extends Component {
    state = {
        rooms: null
    }


    refresh = () => {
        axios.get('/users/1/homes/'+ this.props.houseId + '/rooms/').then(response => {
        this.setState({ rooms: response.data })
        });
    }

    componentDidMount() {
        this.refresh();
    }



    render () {
        let rooms = null;
        if (this.state.rooms) {
        rooms = (this.state.rooms.map(room => {
                    return <GeneralTile url={"/users/1/homes/"+room.home.id + "/rooms/" + room.id} refresh={this.refresh} name={room.name} imageName={room.image} type="room" key={room.id} link={"/dashboard/home/"+room.home.id+"/room/"+room.id}/>;
                }))
        }
        return (
        <div>
        <Grid container spacing={3}>
            {rooms}
            <AddTile name={"Room"} url={'/users/1/homes/'+this.props.houseId+'/rooms/'} refresh={this.refresh}/>
        </Grid>

         </div>
        );
    }
}

export default Rooms;

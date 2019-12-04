import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';
import { Copyright } from './mainDashboard/Dashboard';
import HomeTile from './HomeTile';
import GeneralTile from './GeneralTile';
import { useStyles } from './mainDashboard/Dashboard';
import AddTile from './AddTile';
import axios from 'axios';

class Homes extends Component {

    state = {
        homes: null
    }

    refresh = () => {
        axios.get( '/users/1/homes').then(response => {
            this.setState({ homes: response.data })
        });
    }

    componentDidMount() {
        this.refresh();
    }

    render() {

    let homes = null;
    if (this.state.homes) {
        homes = this.state.homes.map(home => {
            return <GeneralTile refresh={this.refresh} url={"/users/1/homes/"+home.id} name={home.name} imageName={home.image} type="home" key={home.id} link={"/dashboard/home/" + home.id}/>;
        });
    }

    return (




    <div>
            <Grid container spacing={3}>
                {homes}
                <AddTile name={"Home"} url={'/users/1/homes'} refresh={this.refresh}/>
            </Grid>

            <Copyright />
    </div>



    );
}
}


export default Homes;
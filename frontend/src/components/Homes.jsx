
import React from 'react';
import Grid from '@material-ui/core/Grid';
import { Copyright } from './mainDashboard/Dashboard';
import HomeTile from './HomeTile';
import { useStyles } from './mainDashboard/Dashboard'

const Homes = ({ homes }) => {
    return (


        
<div>
            <Grid container spacing={3}>
                {homes.map((home) => (
                    <HomeTile name={home.name} />
                ))}
            </Grid>

            <Copyright />
            </div>
      


    )
}


export default Homes;
import React from 'react';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Tile from './mainDashboard/Tile';

class HomeTile extends React.Component {

    myStyle = {
        root: {
            padding: 5,
            display: 'flex',
            overflow: 'auto',
            flexDirection: 'column',
            height: 240,
            color:'white',
            background:'#1A2980',
            opacity:'0.8'
        }
    }

    render() {
        return (<Grid item xs={12} md={4} lg={3}>
            <Paper style={this.myStyle.root}>
                <Tile name={this.props.name} />
            </Paper>
        </Grid>
        )
    }
}

export default HomeTile;
import React from 'react';
import Dashboard from '../components/mainDashboard/Dashboard';
import axios from 'axios';

class DashboardMainPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            homes: [],
            users: []
        }
    }

    render() {
        return (<Dashboard/>)
    }
}

export default DashboardMainPage;
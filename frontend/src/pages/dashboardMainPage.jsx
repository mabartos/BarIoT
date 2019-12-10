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

    componentDidMount() {
      axios.get( '/users/1/homes').then(response => {
          this.setState({ homes: response.data })
      });
    }
    render() {
        return (<Dashboard homes={this.state.homes}/>)
    }

}

export default DashboardMainPage;

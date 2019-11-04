import React from 'react';
import Dashboard from '../components/mainDashboard/Dashboard';


class DashboardMainPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            homes: [],
            users: []
        }
    }

    fetchHomes() {
        fetch('http://localhost:8080/homes')
            .then(res => res.json())
            .then((data) => {
                console.log(data);
                this.setState({ homes: data })
            }).catch(console.log)

    }

    async componentDidMount() {
        this.fetchHomes();
        setInterval(async () => {
            this.fetchHomes()
        }, 1500);
    }

    render() {
        return (<Dashboard homes={this.state.homes} />)
    }
}

export default DashboardMainPage;
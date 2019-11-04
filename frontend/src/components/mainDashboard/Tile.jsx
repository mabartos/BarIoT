/* eslint-disable no-script-url */
import React from 'react';
import Link from '@material-ui/core/Link';
import Title from './Title';


class Tile extends React.Component {
    myStyle = {
        flex: 1,
    }

    render() {
        return (
            <React.Fragment style={this.myStyle}>
                <Title>{this.props.name}</Title>
                <div>
                    <Link color="primary" href="javascript:;">
                        View balance
                    </Link>
                </div>
            </React.Fragment>
        );
    }
}
export default Tile;


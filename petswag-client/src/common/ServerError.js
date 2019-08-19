import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';

import './ServerError.css';

class NotFound extends Component {
	render() {
		return(
				<Container>
					<div className="server-error-page">
						<h1 className="server-error-title">
							500
						</h1>
						<div className="server-error-desc">
							Something went wrong with the server. Go back?
						</div>
						<Link to="/">
							<Button variant="primary" className="server-error-go-back-btn">
								Go Back
							</Button>
						</Link>
					</div>
				</Container>
		)
	}
}

export default NotFound;
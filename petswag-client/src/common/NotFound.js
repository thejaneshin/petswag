import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap';

import './NotFound.css';

class NotFound extends Component {
	render() {
		return(
				<Container>
					<div className="page-not-found">
						<h1 className="title">
							404
						</h1>
						<div className="desc">
							This is not the page you are looking for.
						</div>
						<Link to="/">
							<Button variant="primary" className="go-back-btn">
								Go Back
							</Button>
						</Link>
					</div>
				</Container>
		)
	}
}

export default NotFound;
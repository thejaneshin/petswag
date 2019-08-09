import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Container, Navbar, Nav } from 'react-bootstrap';

class AppHeader extends Component {
	constructor(props) {
		super(props);
	}

	render() {
		const { currentUser } = this.props;

		return(
			<div>
				<Navbar bg="dark" variant="dark">
					<Navbar.Brand href="/">PetSwag</Navbar.Brand>
					<Navbar.Toggle aria-controls="responsive-navbar-nav" />
  				<Navbar.Collapse id="responsive-navbar-nav" className="justify-content-end">
					{
						currentUser === null
							? (
									<Nav>
										<Nav.Link href="/login">
											Login
										</Nav.Link>

										<Nav.Link href="/signup">
											Register
										</Nav.Link>
									</Nav>

								)
							: (
									<Nav>
										<Nav.Link href="/profile">
											Placeholder Name
										</Nav.Link>

										<Nav.Link href="/activity">
											Activity
										</Nav.Link>
										
										<Nav.Link href="/post/new">
											New Post
										</Nav.Link>
									</Nav>
								)

					}
					</Navbar.Collapse>
				</Navbar>
			</div>
		);

	}

}

export default AppHeader;
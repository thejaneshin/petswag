import React, { Component } from 'react';
import { Container, Navbar, Nav } from 'react-bootstrap';

class AppHeader extends Component {
	constructor(props) {
		super(props);
	}

	render() {
		const { currentUser, isAuthenticated, onLogout } = this.props;

		return(
			<div>
				<Navbar bg="dark" variant="dark">
					<Navbar.Brand href="/">PetSwag</Navbar.Brand>
					<Navbar.Toggle aria-controls="responsive-navbar-nav" />
  				<Navbar.Collapse id="responsive-navbar-nav" className="justify-content-end">
					{
						isAuthenticated
							? (
									<Nav>
										<Nav.Link href="/profile">
											{currentUser.username}
										</Nav.Link>

										<Nav.Link href="/activity">
											Activity
										</Nav.Link>
										
										<Nav.Link href="/post/new">
											New Post
										</Nav.Link>

										<Nav.Link href="#" onSelect={onLogout}>
											Log Out
										</Nav.Link>
									</Nav>

								)
							: (
									<Nav>
										<Nav.Link href="/login">
											Login
										</Nav.Link>

										<Nav.Link href="/signup">
											Register
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
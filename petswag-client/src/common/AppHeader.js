import React, { Component } from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { FaUser, FaBolt, FaPlus } from 'react-icons/fa';

class AppHeader extends Component {
	clickLogout = () => {
		this.props.onLogout();
	}

	render() {
		const { currentUser } = this.props;

		return(
			<Navbar bg="dark" variant="dark">
				<Navbar.Brand href="/">PetSwag</Navbar.Brand>

				<Navbar.Toggle aria-controls="responsive-navbar-nav" />
  			<Navbar.Collapse id="responsive-navbar-nav" className="justify-content-end">
  			{
  				currentUser
  					? (
  							<Nav>
									<Nav.Link href="/profile">
										<FaUser />
									</Nav.Link>

									<Nav.Link href="/activity">
										<FaBolt />
									</Nav.Link>
									
									<Nav.Link href="/post/new">
										<FaPlus />
									</Nav.Link>

									<Nav.Link href="#" onSelect={this.clickLogout}>
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
		);

	}

}

export default AppHeader;
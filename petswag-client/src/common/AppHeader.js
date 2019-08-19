import React, { Component } from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { FaUser, FaBolt, FaPlus } from 'react-icons/fa';

import SearchBar from './SearchBar';

class AppHeader extends Component {

	clickLogout = () => {
		this.props.onLogout();
	}

	render() {
		const { currentUser } = this.props;

		return(
			<Navbar bg="dark" variant="dark" sticky="top" expand="lg">
				<Navbar.Brand href="/">PetSwag</Navbar.Brand>

				<Navbar.Toggle aria-controls="basic-navbar-nav" />

  			<Navbar.Collapse id="basic-navbar-nav" className="justify-content-end">
  			{
  				currentUser
  					? (
  							<Nav>
  								<SearchBar />
									<Nav.Link href={`/users/${currentUser.username}`}>
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
import React, { Component } from 'react';
import { Container, Card, Form, InputGroup, Button, Alert, Col } from 'react-bootstrap';
import { FaUser, FaKey } from 'react-icons/fa';

import { login } from '../../util/APIUtils';
import { ACCESS_TOKEN } from '../../constants';
import './Login.css'

class Login extends Component {
	constructor(props) {
		super(props);
		this.state = {
			usernameOrEmail: '',
			password: '',
			isValid: true,
			errorMessage: ''
		}
	}

	onUsernameOrEmailChange = (event) => {
		this.setState({usernameOrEmail: event.target.value});
	}

	onPasswordChange = (event) => {
		this.setState({password: event.target.value});
	}

	handleSubmit = (event) => {
		event.preventDefault();

		const loginRequest = {
			"usernameOrEmail": this.state.usernameOrEmail,
			"password": this.state.password
		};

		login(loginRequest)
			.then(response => {
				localStorage.setItem(ACCESS_TOKEN, response.accessToken);
				this.props.onLogin();
			})
			.catch(error => {
				if (error.status === 401) {
					this.setState({
						usernameOrEmail: '',
						password: '',
						isValid: false,
						errorMessage: 'Username/Email or Password is incorrect'
					});
				}
				else {
					this.setState({
						usernameOrEmail: '',
						password: '',
						isValid: false,
						errorMessage: 'Something went wrong'
					});
				}
			});
	}

	render() {
		return (
			<Container className="container">
				<div className="d-flex justify-content-center h-100">
					<Card className="card">
						<Card.Header>
							<div className="d-flex justify-content-center">
								<h2>PetSwag</h2>
							</div>
						</Card.Header>

						<Card.Body className="card-body">
							<Form onSubmit={this.handleSubmit}>
								{
									this.state.isValid === false
										? <Alert variant="danger">{this.state.errorMessage}</Alert>
										: (<div></div>) 
								}
								<Form.Row className="d-flex justify-content-center mb-3">
									<Form.Group as={Col} md="12">
										<InputGroup>
											<InputGroup.Prepend>
												<InputGroup.Text>
													<FaUser />
												</InputGroup.Text>
											</InputGroup.Prepend>

											<Form.Control
												type="text"
												name="usernameOrEmail"
												value={this.state.usernameOrEmail}
												onChange={this.onUsernameOrEmailChange}
												placeholder="username / email"
												required />
										</InputGroup>
									</Form.Group>
								</Form.Row>

								<Form.Row className="d-flex justify-content-center mb-3">
									<Form.Group as={Col} md="12">
										<InputGroup>
											<InputGroup.Prepend>
												<InputGroup.Text>
													<FaKey />
												</InputGroup.Text>
											</InputGroup.Prepend>

											<Form.Control
												type="password"
												name="password"
												value={this.state.password}
												onChange={this.onPasswordChange}
												placeholder="password"
												required />
										</InputGroup>
									</Form.Group>
								</Form.Row>

								<div className="text-center">
									<Button type="submit" className="login-btn">Login</Button>
								</div>
							</Form>
						</Card.Body>

						<Card.Footer>
							<div className="d-flex justify-content-center">
								<a href="/signup">New User?</a>
							</div>

							<div className="d-flex justify-content-center">
								<a href="/forgotPassword">Forgot your password?</a>
							</div>

						</Card.Footer>
					</Card>
				</div>
			</Container>
		)
	}
}

export default Login;
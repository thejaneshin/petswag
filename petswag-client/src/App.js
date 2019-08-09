import React, { Component } from 'react';
import { Route, withRouter, Switch, Redirect } from 'react-router-dom';
import { Container, Spinner } from 'react-bootstrap';

import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from './constants';
import AppHeader from './common/AppHeader';
import Login from './user/Login/Login';
import NotFound from './common/NotFound';
import './App.css';

class App extends Component {
  constructor() {
    super();
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
  }

  loadCurrentUser = () => {
    this.setState({ isLoading: true });
localStorage.removeItem(ACCESS_TOKEN);
    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false
        })
      })

  }

  handleLogin = () => {
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  handleLogout = (redirectTo="/") => {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    })

    this.props.history.push(redirectTo);
  }

  componentWillMount() {
    this.loadCurrentUser();
  }

  render() {
    const { currentUser, isAuthenticated, isLoading } = this.state;

    return(
      <div>
        <AppHeader isAuthenticated={isAuthenticated} 
          currentUser={currentUser} 
          onLogout={this.handleLogout} />

        {
          isLoading
            ? (
                <div className="d-flex justify-content-center">
                  <Spinner animation="border" />
                </div>
              )
            :
              (
                <Switch>

                  <Route path="/login"
                    render={(props) => (
                      isAuthenticated 
                        ? (
                            <Redirect to="/" />
                          )
                        : (
                            <Login onLogin={this.handleLogin} {...props} />
                          )
                    )}>     
                  </Route>

                  <Route component={NotFound}></Route>

                </Switch> 
              )

        }

      </div>
    );
  }
}

export default withRouter(App);



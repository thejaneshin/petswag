import React, { Component } from 'react';
import { Route, withRouter, Switch, Redirect } from 'react-router-dom';

import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from './constants';
import AppHeader from './common/AppHeader';
import Login from './user/Login/Login';
import PostList from './post/PostList';
import NotFound from './common/NotFound';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
  }

  loadCurrentUser = () => {
    this.setState({ isLoading: true });

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

  componentWillMount() {
    this.loadCurrentUser();
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

  render() {
    const { currentUser, isAuthenticated, isLoading } = this.state;

    return(
      <div>
        {
          isLoading
            ? (
                null
              )
            :
              (
                <div>
                  <AppHeader isAuthenticated={isAuthenticated} 
                    onLogout={this.handleLogout} />
                  <Switch>
                    <Route exact path="/"
                      render={(props) =>  (
                        isAuthenticated
                          ? (
                              <PostList isAuthenticated={isAuthenticated} 
                                currentUser={currentUser} handleLogout={this.handleLogout} {...props} />
                            )
                          : (
                              <Redirect to="/login" />
                            )
                      )}>
                    </Route>
                     
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
                </div>
              )

        }

      </div>
    );
  }
}

export default withRouter(App);



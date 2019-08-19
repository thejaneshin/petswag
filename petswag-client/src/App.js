import React, { Component } from 'react';
import { Route, withRouter, Switch, Redirect } from 'react-router-dom';
import { Container } from 'react-bootstrap';

import { getCurrentUser } from './util/APIUtils';
import { ACCESS_TOKEN } from './constants';
import LoadingIndicator from './common/LoadingIndicator';
import AppHeader from './common/AppHeader';
import Login from './user/Login';
import PostList from './post/PostList';
import DetailedPost from './post/DetailedPost';
import Profile from './user/Profile';
import NotFound from './common/NotFound';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: true
    }
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  loadCurrentUser = () => {
    this.setState({isLoading: true});

    getCurrentUser()
      .then(response => {
        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      })
      .catch(error => {
        this.setState({isLoading: false});
      })
  }

  handleLogin = () => {
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  handleLogout = (redirectTo="/login") => {
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
      <div className="app">
        {
          isLoading
            ? <LoadingIndicator />
            :
              (
                <div>
                  <AppHeader isAuthenticated={isAuthenticated}
                    currentUser={currentUser}
                    onLogout={this.handleLogout} />

                  <Container>
                    <Switch>
                      <Route exact path="/"
                        render={(props) => (
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

                      <Route path="/posts/:postId"
                        render={(props) => 
                          <DetailedPost isAuthenticated={isAuthenticated}
                            currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />
                        }>
                      </Route>

                      <Route path="/users/:username"
                        render={(props) =>
                          <Profile isAuthenticated={this.state.isAuthenticated} 
                            currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />
                        }>
                      </Route>

                      <Route component={NotFound}></Route>
                    </Switch>
                  </Container>
                </div>
              )

        }

      </div>
    );
  }
}

export default withRouter(App);



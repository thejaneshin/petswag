import React, { Component } from 'react';
import { Row, Col, Button, Modal } from 'react-bootstrap';

import { getUserProfile, getMyFollowingUsernames } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import NotFound from '../common/NotFound';
import ServerError from '../common/ServerError';
import PostList from '../post/PostList';
import FollowList from './FollowList';
import './Profile.css';

class Profile extends Component {
	constructor(props) {
		super(props);
		this.state = {
			user: null,
			myFollowing: [],
			isLoading: true,
			showFollowerList: false,
			showFollowingList: false
		}
	}

	componentDidMount() {
		const username = this.props.match.params.username;
		this.loadUserProfile(username);
		this.loadMyFollowing();
	}

	componentDidUpdate(prevProps) {
		if (this.props.match.params.username !== prevProps.match.params.username) {
			this.loadUserProfile(this.props.match.params.username);
		}     
	}

	loadUserProfile = (username) => {
		this.setState({isLoading: true});

		getUserProfile(username)
			.then(response => {
				this.setState({
					user: response,
					isLoading: false
				});
			})
			.catch(error => {
				if (error.status === 404) {
					this.setState({
						notFound: true,
						isLoading: false
					});
				}
				else {
					this.setState({
						serverError: true,
						isLoading: false
					})
				}
			});
	}

	loadMyFollowing = () => {
		getMyFollowingUsernames()
			.then(response => {
				this.setState({myFollowing: response});
			})
			.catch(error => {
				console.log(error.message);
			})
	}

	handleFollowChange = () => {

	}

	handleShowFollowers = () => {
		this.setState({
			showFollowerList: true,
			showFollowingList: false
		});
	}

	handleCloseFollowers = () => {
		this.setState({showFollowerList: false});
	}

	handleShowFollowing = () => {
		this.setState({
			showFollowingList: true,
			showFollowerList: false
		});
	}

	handleCloseFollowing = () => {
		this.setState({showFollowingList: false});
	}

	render() {
		const { isLoading, notFound, serverError, user, showFollowerList, showFollowingList, myFollowing } = this.state;
		const { currentUser, isAuthenticated, handleLogout } = this.props;

		if (isLoading) {
			return <LoadingIndicator />
		}

		if (notFound) {
			return <NotFound />
		}

		if (serverError) {
			return <ServerError />
		}

		return (
			<div className="profile">
				{
					user
						? (
								<div className="user-profile">
									<div className="user-details">

										<Row>
											<Col md={{ span: 2, offset: 2 }}>
												<img className="user-avatar"
													src={user.avatar}
													alt={user.username}
												/>
											</Col>

											<Col md={{ span: 6, offset: 1 }} className="user-summary">
												<div className="user-username">{user.username}</div>

												{
													currentUser.username === user.username
														? <Button variant="outline-secondary" className="edit-profile-btn">Edit Profile</Button>
														: (
																myFollowing.includes(user.username)
																	? <Button variant="outline-secondary" onClick={this.handleFollowChange} className="user-unfollow-btn">Following</Button>
																	: <Button onClick={this.handleFollowChange} className="user-follow-btn">Follow</Button>
															)
												}
							
												<div className="user-number-stats">
													<span className="user-post-count"><b>{user.postCount}</b> posts</span>

													{
														user.followerCount === 0
															? (
																	<span className="user-follower-zero-count"><b>{user.followerCount}</b> followers</span>
																)
															: (
																	user.followerCount === 1
																		? (
																				<span className="user-follower-count" onClick={this.handleShowFollowers}>
																					<b>{user.followerCount}</b> follower
																				</span>
																			)
																		: (
																				<span className="user-follower-count" onClick={this.handleShowFollowers}>
																					<b>{user.followerCount}</b> followers
																				</span>
																			)

																)

													}

													{
														user.followingCount === 0
															? (
																	<span className="user-following-zero-count"><b>{user.followingCount}</b> following</span>
																)
															: (
																	<span className="user-following-count" onClick={this.handleShowFollowing}>
																		<b>{user.followingCount}</b> following
																	</span>
																)
													}
													
												</div>

												<Modal className="follower-list" show={showFollowerList} onHide={this.handleCloseFollowers} scrollable>
													<FollowList type="FOLLOWER" currentUser={currentUser} username={user.username}
														myFollowing={myFollowing} handleClose={this.handleCloseFollowers} />
												</Modal>

												<Modal className="following-list" show={showFollowingList} onHide={this.handleCloseFollowing} scrollable>
													<FollowList type="FOLLOWING" currentUser={currentUser} username={user.username}
														myFollowing={myFollowing} handleClose={this.handleCloseFollowing} />
												</Modal>

												<div className="user-name"><b>{user.name}</b></div>

												<div className="user-bio">{user.bio}</div>
											</Col>
										</Row>
									</div>

									<hr />

									<PostList username={user.username} isAuthenticated={isAuthenticated} 
                  	currentUser={currentUser} handleLogout={handleLogout} />

								</div>
							)
						: null
				}
			</div>
		);
	}

}

export default Profile;
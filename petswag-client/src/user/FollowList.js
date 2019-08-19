import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Modal, Button } from 'react-bootstrap';

import { getUserFollowers, getUserFollowing } from '../util/APIUtils';
import { FOLLOW_LIST_SIZE } from '../constants';
import './FollowList.css';

class FollowList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			follows: [],
			page: 0,
			size: 10,
			totalElements: 0,
			totalPages: 0, 
			last: true,
			isLoading: true
		}
	}

	componentDidMount() {
		this.loadFollowList();
	}

	loadFollowList = (page = 0, size = FOLLOW_LIST_SIZE) => {
		let promise;

		if (this.props.type === 'FOLLOWER') {
			promise = getUserFollowers(this.props.username, page, size);
		}
		else if (this.props.type === 'FOLLOWING') {
			promise = getUserFollowing(this.props.username, page, size);
		}
		

		if (!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
			.then(response => {
				const follows = this.state.follows.slice();

				this.setState({
					follows: follows.concat(response.content),
					page: response.page,
					size: response.size,
					totalElements: response.totalElements,
					totalPages: response.totalPages,
					last: response.last,
				}, () => {
					this.setState({isLoading: false});
				});
			})
			.catch(error => {
				console.log(error.message);
				this.setState({isLoading: false});
			})
	}

	handleLoadMore = () => {
		this.loadFollowList(this.state.page + 1);
	}

	render() {
		const followViews = [];
		const { follows, last, isLoading } = this.state;
		const { handleClose, currentUser, myFollowing, type } = this.props;

		follows.forEach((follow, followIndex) => {
			followViews.push(
				<div className="follow-info"
					key={follow.id}>
					<Link className="follow-link" onClick={handleClose} to={`/users/${follow.username}`}>	
						<img className="follow-avatar"
								src={follow.avatar}
								alt={follow.username}>
							</img>

						<span className="follow-username">
							{follow.username}
						</span>
					</Link>

					{
						follow.username === currentUser.username
							? null
							: (
									myFollowing.includes(follow.username)
										? null
										: <Button className="follow-btn float-right" size="sm">Follow</Button>
								)
					}
				</div>
			)
		});


		return (
			<div className="follow-list-content">
				<Modal.Header closeButton>
					{
						type === 'FOLLOWER'
							? <Modal.Title className="follow-list-title">Followers</Modal.Title>
							: <Modal.Title className="follow-list-title">Following</Modal.Title>
					}
				</Modal.Header>

				<Modal.Body>
					{followViews}

					{
						!isLoading && !last
							? (
									<div className="load-more-follows-btn">
										<Button variant="outline-primary" onClick={this.handleLoadMore}>
											Load More 
										</Button>
									</div>
								)
							: null

					}
				</Modal.Body>
			</div>
		);
	}
}

export default FollowList;
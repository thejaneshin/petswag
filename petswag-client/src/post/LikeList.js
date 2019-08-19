import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Modal, Button } from 'react-bootstrap';

import { getPostLikes, getMyFollowingUsernames } from '../util/APIUtils';
import { POST_LIST_SIZE } from '../constants';
import './LikeList.css';

class LikeList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			likes: [],
			myFollowing: [],
			page: 0,
			size: 10,
			totalElements: 0,
			totalPages: 0, 
			last: true,
			isLoading: true
		}
	}

	componentDidMount() {
		this.loadLikeList();
		this.loadMyFollowing();
	}

	loadLikeList = (page = 0, size = POST_LIST_SIZE) => {
		this.setState({isLoading: true});

		getPostLikes(this.props.postId, page, size)
			.then(response => {
				const likes = this.state.likes.slice();

				this.setState({
					likes: likes.concat(response.content),
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
		this.loadLikeList(this.state.page + 1);
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

	render() {
		const likeViews = [];
		const { likes, myFollowing, last, isLoading } = this.state;

		likes.forEach((like, likeIndex) => {
			likeViews.push(
				<div className="like-info"
					key={like.id}>
					<Link className="liker-link" to={`/users/${like.likedBy.username}`}>	
						<img className="liker-avatar"
								src={like.likedBy.avatar}
								alt={like.likedBy.username}>
							</img>

						<span className="liker-username">
							{like.likedBy.username}
						</span>
					</Link>

					{
						like.likedBy.username === this.props.currentUser.username
							? null
							: (
									myFollowing.includes(like.likedBy.username)
										? null
										: <Button className="follow-btn ml-auto" size="sm">Follow</Button>
								)
					}
				</div>
			)
		});

		return(
			<div className="like-list-content">
				<Modal.Header closeButton>
					<Modal.Title className="like-list-title">Likes</Modal.Title>
				</Modal.Header>

				<Modal.Body>
					{likeViews}

					{
						!isLoading && !last
							? (
									<div className="load-more-likes-btn">
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

export default LikeList;
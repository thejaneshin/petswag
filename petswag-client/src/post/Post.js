import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Card, Row, Form, InputGroup, Button } from 'react-bootstrap';
import { FaRegHeart, FaHeart } from 'react-icons/fa';
import { IconContext } from 'react-icons';

import { formatDateTime } from '../util/Helpers';
import { getPostComments, isLikedByMe, changeLike, addComment } from '../util/APIUtils';
import { COMMENT_LIST_SIZE } from '../constants';
import './Post.css';

class Post extends Component {
	constructor(props) {
		super(props);
		this.state = {
			currentComments: [],
			page: 0,
			size: 5,
			totalElements: 0,
			totalPages: 0, 
			last: true,
			isLiked: false,
			likeCount: this.props.post.likeCount,
			comment: ''
		}
	}

	componentDidMount() {
		this.getLikeStatus();
		this.loadCommentList();
	}

	loadCommentList = (page = 0, size = COMMENT_LIST_SIZE) => {
		getPostComments(this.props.post.id, page, size)
			.then(response => {
				const currentComments = this.state.currentComments.slice();

				this.setState({
					currentComments: currentComments.concat(response.content),
					page: response.page,
					size: response.size,
					totalElements: response.totalElements,
					totalPages: response.totalPages,
					last: response.last
				});
			})
			.catch(error => {
				console.log(error.message);
			})
	}

	handleLoadMore = () => {
		this.loadCommentList(this.state.page + 1);
	}

	getLikeStatus = () => {
		isLikedByMe(this.props.post.id)
			.then(response => {
				this.setState({isLiked: response})
			})
			.catch (error => {
				if (error.status === 401) {
					this.setState({isLiked: false})
				}
				else {
					console.log(error.message);
				}
			})
	}

	handleLikeChange = () => {
		if (!this.props.isAuthenticated) {
			this.props.history.push("/login");
			return;
		}

		changeLike(this.props.post.id)
			.then(response => {
				if (this.state.isLiked) {
					this.setState({isLiked: false, likeCount: this.state.likeCount - 1});
				}
				else {
					this.setState({isLiked: true, likeCount: this.state.likeCount + 1});
				}
			})
			.catch(error => {
				if (error.status === 401) {
					this.props.handleLogout("/")
				}
				else {
					console.log(error.message);
				}
			})
	}

	onCommentChange = (event) => {
		this.setState({comment: event.target.value});
	}

	handleCommentSubmit = (event) => {
		event.preventDefault();

		if (!this.props.isAuthenticated) {
			this.props.history.push("/login");
			return;
		}

		const commentRequest = {
			text: this.state.comment
		};

		addComment(this.props.post.id, commentRequest)
			.then(response => {
				window.location.reload();
			})
			.catch(error => {
				if (error.status === 401) {
					this.props.handleLogout("/")
				}
				else {
					console.log(error.message);
				}
			})
	}

	render() {
		const commentViews = [];
		const { post } = this.props;
		const { currentComments, isLiked, likeCount, comment } = this.state;

		currentComments.forEach((comment, commentIndex) => {
			commentViews.push(
				<div className="comment-info"
					key={comment.id}>
					<Link className="commenter-link" to={`/users/${comment.commentedBy.username}`}>	
						<span className="commenter-username">
							{comment.commentedBy.username}
						</span>
					</Link>

					<span className="comment-text">
						{comment.text}
					</span>
				</div>
			)
		});

		return(
			<Card className="post-content">

				<Card.Header className="post-header">
					<Row className="post-creator-info align-items-center">
						<Link className="creator-link" to={`/users/${post.createdBy.username}`}>
							<img className="post-creator-avatar"
								src={post.createdBy.avatar}
								alt={post.createdBy.username}>
							</img>

							<span className="post-creator-username">
								{post.createdBy.username}
							</span>
						</Link>

						<span className="post-creation-time ml-auto">
							{formatDateTime(post.postTime)}
						</span>
					</Row>

				</Card.Header>

				<Card.Img className="post-image" 
					variant="top" 
					src={post.image} 
					alt=""/>

				<Card.Footer>
					<IconContext.Provider value={{ color: "red", size: "2em" }}>
						<div className="like-btn" onClick={this.handleLikeChange}>
							{
								isLiked
									? <FaHeart />
									: <FaRegHeart />
							}
						</div>
					</IconContext.Provider>

					{/*Need to implement view all likes link*/}
					<a href="#" className="total-likes">
						{
							likeCount === 1
								? (
										<div>{`${likeCount} like`}</div>
									)
								: (
										<div>{`${likeCount} likes`}</div>
									)
						}
					</a>

					{
						post.caption
							? (
									<div className="caption-content">
										<Link className="post-caption-user-link" to={`/users/${post.createdBy.username}`}>	
											<span className="post-caption-username">
												{post.createdBy.username}
											</span>
										</Link>

										<span className="post-caption">
											{post.caption}
										</span>
									</div>
								)
							: <div className="caption-content"></div>
					}

					{/*Need to implement view all comments link*/}
					<div className="total-comments">
						{
							post.commentCount <= COMMENT_LIST_SIZE
								? null
								: (
										<Link to={`/posts/${post.id}`}>
											<span>{`View all ${post.commentCount} comments`}</span>
										</Link>
									)
						}
					</div>

					{commentViews}

					<div className="comment-form">
						<Form onSubmit={this.handleCommentSubmit} autoComplete="off">
							<Form.Group as={Row}>
								<InputGroup className="mb-3">
								<Form.Control
									type="text"
									name="comment"
									value={comment}
									onChange={this.onCommentChange}
									placeholder="Add a comment..."
									required />

									<InputGroup.Append>
										<Button type="submit" className="comment-btn" size="sm">Submit</Button>
									</InputGroup.Append>

								</InputGroup>
							</Form.Group>
						</Form>
					</div>

				</Card.Footer>

			</Card>
		);

	}
}

export default withRouter(Post);
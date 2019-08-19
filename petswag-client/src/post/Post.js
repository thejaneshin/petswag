import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Card, Row, Form, InputGroup, Button, Modal } from 'react-bootstrap';
import { FaRegHeart, FaHeart } from 'react-icons/fa';
import { IconContext } from 'react-icons';

import { formatDateTime } from '../util/Helpers';
import { getPostComments, isLikedByMe, changeLike, addComment } from '../util/APIUtils';
import { COMMENT_LIST_SIZE } from '../constants';
import LikeList from './LikeList';
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
			comment: '',
			showLikeList: false,
			isLoading: true
		}
	}

	componentDidMount() {
		this.loadLikeStatus();
		this.loadCommentList();
	}

	loadCommentList = (page = 0, size = COMMENT_LIST_SIZE) => {
		this.setState({isLoading: true});

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
				}, () => {
					this.setState({isLoading: false})
				});
			})
			.catch(error => {
				console.log(error.message);
			})
	}

	handleLoadMore = () => {
		this.loadCommentList(this.state.page + 1);
	}

	loadLikeStatus = () => {
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

	handleShowLikes = () => {
		this.setState({showLikeList: true})
	}

	handleCloseLikes = () => {
		this.setState({showLikeList: false})
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
		const { post, currentUser } = this.props;
		const { currentComments, isLoading, isLiked, likeCount, comment, last, totalPages, showLikeList } = this.state;

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

		if (isLoading) {
			return null;
		}

		return(
			<Card className="post-content">

				{/*Card header shows post's username, avatar, and time of post creation*/}
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
					<Row className="like-info">
						{/*Like button*/}
						<IconContext.Provider value={{ color: "red", size: "2em" }}>
							<div className="like-btn" onClick={this.handleLikeChange}>
								{
									isLiked
										? <FaHeart />
										: <FaRegHeart />
								}
							</div>
						</IconContext.Provider>

						{/*Like count with link to modal of likers' info*/}
						{
							likeCount === 0
								? (
										<div className="total-no-likes">
											Be the first to like this post!
										</div>
									)
								: (
										<div className="total-likes" onClick={this.handleShowLikes}>
											{
												likeCount === 1
													? (
															<div>{`${likeCount} like`}</div>
														)
													: (
															<div>{`${likeCount} likes`}</div>
														)

											}
										</div>
									)
						}
					</Row>

					<Modal className="like-list" show={showLikeList} onHide={this.handleCloseLikes} scrollable>
						<LikeList postId={post.id} currentUser={currentUser} />
					</Modal>

					{/*Post caption*/}
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

					<hr/>

					{
						this.props.type === 'DETAILED_POST'
							? (
									<div className="detailed-comments">
									{
										totalPages <= 1
											? (
													<div className="detailed-comments-one-page">
														{commentViews}
													</div>
												)
											: (
													<div className="detailed-comments-load">
														{commentViews}

														{
															!isLoading && !last
																? (
																		<div className="load-more-comments">
																			<Button variant="outline-primary" onClick={this.handleLoadMore} disabled={isLoading}>
																				Load More 
																			</Button>
																		</div>
																	)
																: null
														}
													</div>
												)
									}
									</div>
								)
							: (
									<div className="postlist-comments">
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
									</div>
								)
					}
					

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
										{
											comment.length === 0
												? <Button type="submit" variant="secondary" className="comment-btn" size="sm" disabled>Submit</Button>
												: <Button type="submit" className="comment-btn" size="sm">Submit</Button>
										}
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
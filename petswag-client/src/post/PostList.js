import React, { Component } from 'react';
import { Container, Button } from 'react-bootstrap';

import Post from './Post';
import { getUserPosts, getFollowingPosts } from '../util/APIUtils';
import { POST_LIST_SIZE } from '../constants';
import './PostList.css';

class PostList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			posts: [],
			page: 0,
			size: 5,
			totalElements: 0,
			totalPages: 0, 
			last: true,
			isLoading: true
		}
	}

	componentDidMount() {
		this.loadPostList();
	}

	componentDidUpdate(prevProps) {
		if (this.props.isAuthenticated !== prevProps.isAuthenticated) {
			this.setState({
				posts: [],
				page: 0,
				size: 5,
				totalElements: 0,
				totalPages: 0, 
				last: true,
				isLoading: true
			});

			this.loadPostList();
		}
	}

	loadPostList = (page = 0, size = POST_LIST_SIZE) => {
		let promise;

		if (this.props.username) {
			promise = getUserPosts(this.props.username, page, size)
		}
		else {
			promise = getFollowingPosts(page, size);
		}
		

		if (!promise) {
			return;
		}

		this.setState({isLoading: true});

		promise
			.then(response => {
				const posts = this.state.posts.slice();

				this.setState({
					posts: posts.concat(response.content),
					page: response.page,
					size: response.size,
					totalElements: response.totalElements,
					totalPages: response.totalPages,
					last: response.last
				}, () => {
					this.setState({isLoading: false});
				});
			})
			.catch(error => {
				this.setState({isLoading: false});
				console.log(error.message);
			})
	}

	handleLoadMore = () => {
		this.loadPostList(this.state.page + 1);
	}

	render() {
		const postViews = [];
		const { posts, isLoading, last } = this.state;

		posts.forEach((post, postIndex) => {
			postViews.push(
				<Post 
					key={post.id}
					post={post}
					isAuthenticated={this.props.isAuthenticated}
					currentUser={this.props.currentUser}
				/>
			)
		});

		return(
			<Container>
				{postViews}
				
				{
					!isLoading && posts.length === 0
						? (
								<div className="no-posts-found">
									<span>No Posts Found.</span>
								</div>
							)
						: null

				}

				{
					!isLoading && !last
						? (
								<div className="load-more-posts">
									<Button variant="outline-primary" onClick={this.handleLoadMore} disabled={isLoading}>
										Load More 
									</Button>
								</div>
							)
						: null
				}

			</Container>


		);
	}
}

export default PostList;
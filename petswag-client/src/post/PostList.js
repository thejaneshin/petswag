import React, { Component } from 'react';
import { Container, Spinner } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';

import Post from './Post';
import { getFollowingPosts } from '../util/APIUtils';
import { POST_LIST_SIZE} from '../constants';

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
			isLoading: false
		}
	}

	loadPostList = (page = 0, size = POST_LIST_SIZE) => {
		let promise;
		if (this.props.username) {


		}
		else {
			promise = getFollowingPosts(page, size);
		}

		if (!promise) {
			return;
		}

		this.setState({
			isLoading: true
		})

		promise
			.then(response => {
				const posts = this.state.posts.slice();

				this.setState({
					posts: posts.concat(response.content),
					page: response.page,
					size: response.size,
					totalElements: response.totalElements,
					totalPages: response.totalPages,
					last: response.last,
					isLoading: false
				})
			})
			.catch(error => {
				this.setState({
					isLoading: false
				})
			});

	}

	componentDidMount() {
		this.loadPostList();
	}

	componentDidUpdate(nextProps) {
		if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
			// Reset state
			this.setState({
				posts: [],
				page: 0,
				size: 5,
				totalElements: 0,
				totalPages: 0, 
				last: true,
				isLoading: false
			});
			this.loadPostList();

		}
	}

	handleLoadMore = () => {
		this.loadPostList(this.state.page + 1);
	}

	render() {
		const postViews = [];

		this.state.posts.forEach((post, postIndex) => {
			postViews.push(
				<Post 
					key={post.id}
					post={post}
				/>
			)
		});

		return(
			<Container>
				{postViews}
			</Container>
		);
	}
}

export default withRouter(PostList);
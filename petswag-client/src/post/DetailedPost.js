import React, { Component } from 'react';
import { Container } from 'react-bootstrap';

import { getPost } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import NotFound from '../common/NotFound';
import ServerError from '../common/ServerError';
import Post from './Post';

class DetailedPost extends Component {
	constructor(props) {
		super(props);
		this.state = {
			post: null,
			isLoading: false
		}
	}

	componentDidMount() {
		const postId = this.props.match.params.postId;
		this.loadPost(postId);
	}

	componentDidUpdate(prevProps) {
		if (this.props.match.params.postId !== prevProps.match.params.postId) {
			this.loadPost(this.props.match.params.postId);
		}
	}

	loadPost(postId) {
		this.setState({isLoading: true});
   	
	   	getPost(postId)
	      .then(response => {
	        this.setState({
	        	post: response,
	        	isLoading: false
	        })
	      })
	      .catch(error => {
	        if (error.status === 404 || error.status === 400) {
	        	this.setState({
	        		notFound: true,
	        		isLoading: false
	        	})
	        }
	        else {
	        	this.setState({
	        		serverError: true,
	        		isLoading: false
	        	})
	        }
	      });
  	}

	render() {
		const { isLoading, notFound, serverError, post } = this.state;

		if (isLoading) {
			return <LoadingIndicator />
		}

		if (notFound) {
			return <NotFound />
		}

		if (serverError) {
			return <ServerError />
		}

		return(
			<Container>
				{
					this.state.post
						? (
								<Post post={post} type="DETAILED_POST" 
									currentUser={this.props.currentUser} isAuthenticated={this.props.isAuthenticated}/>
							)
						: null
				}
			</Container>
		);
	}

}

export default DetailedPost;
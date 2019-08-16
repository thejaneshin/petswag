import React, { Component } from 'react';
import { Container, Spinner } from 'react-bootstrap';

import { getPost } from '../util/APIUtils';
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

	componentDidUpdate(nextProps) {
		if (this.props.match.params.postId !== nextProps.match.params.postId) {
			this.loadPost(nextProps.match.params.postId);
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
        if (error.status === 404) {
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
		if (this.state.isLoading) {
			return <Spinner animation="border" variant="primary" />
		}
		return(
			<Container>
				{
					this.state.post
						? (
								<Post post={this.state.post} type="DETAILED_POST" />
							)
						: null

				}
			</Container>
		);
	}

}

export default DetailedPost;
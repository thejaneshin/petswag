import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Card } from 'react-bootstrap';

import { formatDateTime } from '../util/Helpers';
import './Post.css';

class Post extends Component {
	constructor(props) {
		super(props);
		this.state = {

		}
	}


	render() {
		const { post } = this.props;

		return(
			<Card className="post-content">
				<Card.Header className="post-header">
					<div className="post-creator-info">
						<Link className="creator-link" to={`/users/${post.createdBy.username}`}>

							{/*Will add avatar later*/}
							<span className="post-creator-username">
								{post.createdBy.username}
							</span>

							<span className="post-creation-time">
								{ formatDateTime(post.postTime)}
							</span>
						</Link>
					</div>

				</Card.Header>

				<Card.Img className="post-image" variant="top" src={post.image} />
				


				<Card.Footer>
					{/*Will add like button later*/}

					<span className="total-likes">
						{
							post.likeCount === 1
								? (
										<div>{`${post.likeCount} like`}</div>
									)
								: (
										<div>{`${post.likeCount} likes`}</div>
									)
						}
					</span>

					<span className="total-comments">
						{
							post.commentCount === 1
								? (
										<div>{`${post.commentCount} comment`}</div>
									)
								: (
										<div>{`${post.commentCount} comments`}</div>
									)
						}
					</span>

					<span className="post-caption">
						{post.caption}
					</span>

					{/*Will add comment list later*/}

					{/*Will add comment form later*/}
				</Card.Footer>
			</Card>
		);

	}
}

export default Post;
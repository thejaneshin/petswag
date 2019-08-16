import React from 'react';
import { Spinner } from 'react-bootstrap';

import './LoadingIndicator.css';

export default function() {
	return(
		<div className="spinner">
			<Spinner animation="border" variant="primary" />
		</div>
	)
}
import React, { Component } from 'react';
import { Form, FormControl } from 'react-bootstrap';

class SearchBar extends Component {
	constructor(props) {
		super(props);
		this.state = {
			searchText: ''
		}
	}

	onSearchChange = (event) => {
		this.setState({searchText: event.target.value});
		
		if (event.target.value.length > 0) {
			console.log(event.target.value);
		}

		// Need to add in helper function or Rest API endpoint for
		// finding matching users
		/*
			Order hierarchy:
			-Exact match
			-People you follow that kinda match (alphabetic)
			-People that follow you that kinda match (alphabetic)
			-The rest (alphabetic)
		*/
	}

	onSearchSubmit = (event) => {
		event.preventDefault();


	}


	render() {
		const { searchText } = this.state;

		return(
			<div className="searchbar">
				<Form inline onSubmit={this.onSearchSubmit} className="mr-2">
					<FormControl 
						type="text"
						name="searchText"
						placeholder="Search"
						value={searchText}
						onChange={this.onSearchChange}
						required />
				</Form>
			</div>
		);
	}
}

export default SearchBar;
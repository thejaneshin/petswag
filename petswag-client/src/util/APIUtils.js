import { API_BASE_URL, ACCESS_TOKEN, POST_LIST_SIZE, LIKE_LIST_SIZE, COMMENT_LIST_SIZE, FOLLOW_LIST_SIZE } from '../constants';

const request = (options) => {
	const headers = new Headers({
		'Content-Type': 'application/json'
	});

	if (localStorage.getItem(ACCESS_TOKEN)) {
		headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
	}

	const defaults = {headers: headers};
	options = Object.assign({}, defaults, options);

	return fetch(options.url, options)
		.then(response => 
			response.json().then(json => {
				if (!response.ok) {
					return Promise.reject(json);
				}
				return json;
			})
		);
};

export function signup(signupRequest) {
	return request({
		url: API_BASE_URL + "/auth/signup",
		method: 'POST',
		body: JSON.stringify(signupRequest)
	});
}

export function login(loginRequest) {
	return request({
		url: API_BASE_URL + "/auth/signin",
		method: 'POST',
		body: JSON.stringify(loginRequest)
	});
}

export function getCurrentUser() {
	if (!localStorage.getItem(ACCESS_TOKEN)) {
		return Promise.reject("No access token set.");
	}

	return request({
		url: API_BASE_URL +"/user/me",
		method: 'GET'
	});
}

export function getUserProfile(username) {
	return request({
		url: API_BASE_URL + "/users/" + username,
		method: 'GET'
	});
}

export function getUserPosts(username, page, size) {
	page = page || 0;
	size = size || POST_LIST_SIZE;
	return request({
		url: API_BASE_URL + "/users/" + username + "/posts?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function getMyFollowingUsernames() {
	return request({
		url: API_BASE_URL + "/user/me/following",
		method: 'GET'
	});
}

export function getUserFollowers(username, page, size) {
	page = page || 0;
	size = size || FOLLOW_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/users/" + username + "/followers?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function getUserFollowing(username, page, size) {
	page = page || 0;
	size = size || FOLLOW_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/users/" + username + "/following?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function getFollowingPosts(page, size) {
	page = page || 0;
	size = size || POST_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/dashboard?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function getPost(postId) {
	return request({
		url: API_BASE_URL + "/posts/" + postId,
		method: 'GET'
	});
}

export function isLikedByMe(postId) {
	return request({
		url: API_BASE_URL + "/posts/" + postId + "/liked",
		method: 'GET'
	});
}

export function changeLike(postId) {
	return request({
		url: API_BASE_URL + "/posts/" + postId + "/likes",
		method: 'POST'
	});
}

export function getPostLikes(postId, page, size) {
	page = page || 0;
	size = size || LIKE_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/posts/" + postId + "/likes?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function getPostComments(postId, page, size) {
	page = page || 0;
	size = size || COMMENT_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/posts/" + postId + "/comments?page=" + page + "&size=" + size,
		method: 'GET'
	});
}

export function addComment(postId, commentRequest) {
	return request({
		url: API_BASE_URL + "/posts/" + postId + "/comments",
		method: 'POST',
		body: JSON.stringify(commentRequest)
	});
}

import { API_BASE_URL, ACCESS_TOKEN, POST_LIST_SIZE } from '../constants';

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

export function getFollowingPosts(page, size) {
	page = page || 0;
	size = size || POST_LIST_SIZE;

	return request({
		url: API_BASE_URL + "/dashboard?page=" + page + "&size=" + size,
		method: 'GET'
	});
}
package com.thejaneshin.petswag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.service.PostService;
import com.thejaneshin.petswag.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return userService.findByUsername(username);
	}
	
	@GetMapping("/users/{username}")
	public User getUser(@PathVariable(value="username") String username) {
		return userService.findByUsername(username);
	}
	
	@GetMapping("/users/{username}/posts")
	public List<Post> getUserPosts(@PathVariable(value="username") String username) {
		return postService.findByUsername(username);
	}
	
	@GetMapping("/users/{username}/followers")
	@PreAuthorize("hasRole('USER')")
	public List<User> getUserFollowers(@PathVariable(value="username") String username) {
		return userService.getUserFollowers(username);
	}
	
	@GetMapping("/users/{username}/following")
	@PreAuthorize("hasRole('USER')")
	public List<User> getUserFollowing(@PathVariable(value="username") String username) {
		return userService.getUserFollowing(username);
	}
}

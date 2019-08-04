package com.thejaneshin.petswag.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.service.PostService;
import com.thejaneshin.petswag.service.UserService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/posts")
	public List<Post> getAllPosts() {
		return postService.findAll();
	}
	
	@GetMapping("/posts/{postId}")
	public Post getPost(@PathVariable int postId) {
		return postService.findById(postId);
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('USER')")
	public List<Post> getFollowingPosts() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		return postService.findFollowingPosts(username);
	}
	
	@PostMapping("/posts")
	@PreAuthorize("hasRole('USER')")
	public Post createPost(@Valid @RequestBody Post thePost) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User user = userService.findByUsername(username);
	
		thePost.setId(0);
		thePost.setUser(user);
		
		postService.save(thePost);
		return thePost;
	}
	
	// User should only be allowed to edit caption
	@PutMapping("/posts")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updatePost(@Valid @RequestBody Post thePost) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User user = userService.findByUsername(username);
		Post post = postService.findById(thePost.getId());
		
		if (post == null)
			return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
		
		// Check if the post belongs to current user
		// Makes sure only caption changes
		if (user.getId() == post.getUser().getId()) {
			post.setCaption(thePost.getCaption());
			postService.save(post);
			return new ResponseEntity<>("Post successfully edited", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Unable to edit post", HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@DeleteMapping("/posts/{postId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deletePost(@PathVariable int postId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User user = userService.findByUsername(username);		
		Post post = postService.findById(postId);
		
		if (post == null)
			return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
		
		if (user.getId() == post.getUser().getId()) {
			postService.deleteById(postId);
			return new ResponseEntity<>("Post successfully deleted", HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>("Unable to delete post", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
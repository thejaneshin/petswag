package com.thejaneshin.petswag.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.thejaneshin.petswag.payload.EditCaptionRequest;
import com.thejaneshin.petswag.payload.PostRequest;
import com.thejaneshin.petswag.payload.PostResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.security.CurrentUser;
import com.thejaneshin.petswag.security.UserPrincipal;
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
	public List<PostResponse> getAllPosts() {
		return postService.findAll();
	}
	
	@GetMapping("/posts/{postId}")
	public PostResponse getPost(@PathVariable int postId) {
		Post post = postService.findById(postId);	
		User user = userService.findByUsername(post.getUser().getUsername());
		
		UserSummary createdBy = new UserSummary(user.getId(), user.getUsername(), user.getName());
		
		return new PostResponse(post.getId(), post.getImage(), post.getCaption(),
				createdBy, post.getPostTime(), post.getLikes().size(), post.getComments().size());
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('USER')")
	public List<PostResponse> getFollowingPosts(@CurrentUser UserPrincipal currentUser) {
		return postService.findFollowingPosts(currentUser.getUsername());
	}
	
	@PostMapping("/posts")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPost(@CurrentUser UserPrincipal currentUser, 
			@Valid @RequestBody PostRequest postRequest) {
		User user = userService.findByUsername(currentUser.getUsername());
	
		Post post = new Post(postRequest.getImage(), postRequest.getCaption(), LocalDateTime.now(), user);
		post.setId(0);
		
		postService.save(post);
		return new ResponseEntity<>("Post successfully created!", HttpStatus.OK);
	}

	@PutMapping("/posts/{postId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updatePost(@CurrentUser UserPrincipal currentUser, 
			@Valid @RequestBody EditCaptionRequest editCaptionRequest, @PathVariable int postId) {
		User user = userService.findByUsername(currentUser.getUsername());
		Post post = postService.findById(postId);
		
		if (post == null)
			return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
		
		if (user.getId() == post.getUser().getId()) {
			post.setCaption(editCaptionRequest.getCaption());
			postService.save(post);
			return new ResponseEntity<>("Post successfully edited!", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Unable to edit post", HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@DeleteMapping("/posts/{postId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deletePost(@CurrentUser UserPrincipal currentUser, @PathVariable int postId) {	
		User user = userService.findByUsername(currentUser.getUsername());		
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
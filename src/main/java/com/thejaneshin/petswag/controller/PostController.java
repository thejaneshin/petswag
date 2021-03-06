package com.thejaneshin.petswag.controller;

import java.time.LocalDateTime;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thejaneshin.petswag.exception.ResourceNotFoundException;
import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.ApiResponse;
import com.thejaneshin.petswag.payload.CommentRequest;
import com.thejaneshin.petswag.payload.CommentResponse;
import com.thejaneshin.petswag.payload.EditCaptionRequest;
import com.thejaneshin.petswag.payload.LikeResponse;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.PostRequest;
import com.thejaneshin.petswag.payload.PostResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.security.CurrentUser;
import com.thejaneshin.petswag.security.UserPrincipal;
import com.thejaneshin.petswag.service.CommentService;
import com.thejaneshin.petswag.service.LikeService;
import com.thejaneshin.petswag.service.PostService;
import com.thejaneshin.petswag.service.UserService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/posts/{postId}")
	public PostResponse getPost(@PathVariable int postId) {
		Post post = postService.findById(postId);
		
		if (post == null)
			throw new ResourceNotFoundException("Post", "postId", postId);
		
		User user = userService.findByUsername(post.getUser().getUsername());
		
		UserSummary createdBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
		
		return new PostResponse(post.getId(), post.getImage(), post.getCaption(),
				createdBy, post.getPostTime(), post.getLikes().size(), post.getComments().size());
	}
	
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('USER')")
	public PagedResponse<PostResponse> getFollowingPosts(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="5") int size) {

		return postService.findFollowingPosts(currentUser.getUsername(), page, size);
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
	
	@GetMapping("/posts/{postId}/likes")
	public PagedResponse<LikeResponse> getPostLikes(@PathVariable int postId,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="10") int size) {
		
		return likeService.findByPostId(postId, page, size);
	}
	
	@GetMapping("/posts/{postId}/liked")
	@PreAuthorize("hasRole('USER')")
	public boolean isLikedByMe(@CurrentUser UserPrincipal currentUser, @PathVariable int postId) {
		Like like = likeService.findByPostIdAndUsername(postId, currentUser.getUsername());
		
		if (like == null)
			return false;
		else
			return true;
	}
	
	@PostMapping("/posts/{postId}/likes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> likePost(@CurrentUser UserPrincipal currentUser, @PathVariable int postId) {
		User me = userService.findByUsername(currentUser.getName());
		Post post = postService.findById(postId);
		
		if (post == null)
			return new ResponseEntity<>(new ApiResponse(false, "Post does not exist"),
                    HttpStatus.NOT_FOUND);
		
		Like like = likeService.findByPostIdAndUsername(postId, me.getUsername());
		
		if (like != null) {
			likeService.deleteById(like.getId());
		}
		else {
			like = new Like(me, post);
			like.setLikeTime(LocalDateTime.now());
			likeService.save(like);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public PagedResponse<CommentResponse> getPostComments(@PathVariable int postId,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="5") int size) {
		
		return commentService.findByPostId(postId, page, size);
	}
	
	@PostMapping("/posts/{postId}/comments")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> commentPost(@CurrentUser UserPrincipal currentUser, @PathVariable int postId,
			@Valid @RequestBody CommentRequest commentRequest) {
		User me = userService.findByUsername(currentUser.getName());
		Post post = postService.findById(postId);
		
		if (post == null)
			return new ResponseEntity<>(new ApiResponse(false, "Post does not exist"),
                    HttpStatus.NOT_FOUND);
		
		Comment comment = new Comment(commentRequest.getText(), LocalDateTime.now(), me, post);
		commentService.save(comment);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
}
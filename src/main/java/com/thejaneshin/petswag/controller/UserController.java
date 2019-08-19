package com.thejaneshin.petswag.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thejaneshin.petswag.exception.ResourceNotFoundException;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.ApiResponse;
import com.thejaneshin.petswag.payload.ChangePasswordRequest;
import com.thejaneshin.petswag.payload.EditProfileRequest;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.PostResponse;
import com.thejaneshin.petswag.payload.UserProfile;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.security.CurrentUser;
import com.thejaneshin.petswag.security.UserPrincipal;
import com.thejaneshin.petswag.service.PostService;
import com.thejaneshin.petswag.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), 
				userService.findByUsername(currentUser.getUsername()).getAvatar());
        return userSummary;
	}
	
	@GetMapping("/user/me/following")
	@PreAuthorize("hasRole('USER')")
	public List<String> getMyFollowingNames(@CurrentUser UserPrincipal currentUser) {
		return userService.getUserFollowingNames(currentUser.getUsername());
	}
	
	@PutMapping("/user/me/editProfile")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> editProfile(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody EditProfileRequest editProfileRequest) {
		User me = userService.findByUsername(currentUser.getUsername());
		
		if (!currentUser.getUsername().equals(editProfileRequest.getUsername())
				&& userService.existsByUsername(editProfileRequest.getUsername()))
			return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
		
		if (!currentUser.getEmail().equals(editProfileRequest.getEmail())
				&& userService.existsByEmail(editProfileRequest.getEmail()))
			return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
		
		me.setUsername(editProfileRequest.getUsername());
		me.setEmail(editProfileRequest.getEmail());
		me.setName(editProfileRequest.getName());
		me.setBio(editProfileRequest.getBio());
		me.setAvatar(editProfileRequest.getAvatar());
		
		userService.save(me);
		return new ResponseEntity<>("Profile successfully updated!", HttpStatus.OK);
	}
	
	@PutMapping("/user/me/updatePassword")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updatePassword(@CurrentUser UserPrincipal currentUser, 
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		User me = userService.findByUsername(currentUser.getUsername());
		
		if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), currentUser.getPassword()))
			return new ResponseEntity<>(new ApiResponse(false, "Entered incorrect old password"),
                    HttpStatus.BAD_REQUEST);
		
		me.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
		userService.save(me);
		return new ResponseEntity<>("Password successfully updated!", HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}")
	public UserProfile getUser(@PathVariable(value="username") String username) {
		User user = userService.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		int postCount = postService.countByUsername(username);
		int followingCount = userService.countFollowing(user.getUsername());
		int followerCount = userService.countFollowers(user.getUsername());
		
		UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getBio(),
				user.getAvatar(), postCount, followingCount, followerCount);
		
		return userProfile;
	}
	
	@GetMapping("/users/{username}/posts")
	public PagedResponse<PostResponse> getUserPosts(@PathVariable(value="username") String username,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="5") int size) {
		
		return postService.findByUsernamePage(username, page, size);
	}
	
	@GetMapping("/users/{username}/followers")
	@PreAuthorize("hasRole('USER')")
	public PagedResponse<UserSummary> getUserFollowers(@PathVariable(value="username") String username,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="10") int size) {	
		
		return userService.getUserFollowers(username, page, size);
	}
	
	@GetMapping("/users/{username}/following")
	@PreAuthorize("hasRole('USER')")
	public PagedResponse<UserSummary> getUserFollowing(@PathVariable(value="username") String username,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="10") int size) {
		
		return userService.getUserFollowing(username, page, size);
	}
	
	@PostMapping("/users/{username}/follow")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> followUser(@CurrentUser UserPrincipal currentUser, 
			@PathVariable(value="username") String username) {
		User user = userService.findByUsername(username);
		User me = userService.findByUsername(currentUser.getName());
		
		if (user == null)
			return new ResponseEntity<>(new ApiResponse(false, "User does not exist"),
                    HttpStatus.NOT_FOUND);
		
		if(user.equals(me))
			return new ResponseEntity<>(new ApiResponse(false, "You can't follow yourself"),
                    HttpStatus.BAD_REQUEST);
		
		if (me.getFollowingList().contains(user)) {
			me.getFollowingList().remove(user);
			user.getFollowerList().remove(me);
			userService.save(me);
			return new ResponseEntity<>("Unfollowing " + user.getUsername(), HttpStatus.OK);
		}
		else {
			me.getFollowingList().add(user);
			userService.save(me);
			return new ResponseEntity<>("Following " + user.getUsername(), HttpStatus.OK);
		}
	}
	
}

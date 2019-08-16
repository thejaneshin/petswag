package com.thejaneshin.petswag.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.exception.BadRequestException;
import com.thejaneshin.petswag.exception.ResourceNotFoundException;
import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.PostResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.PostRepository;
import com.thejaneshin.petswag.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {
	public PostRepository postRepository;
	public UserRepository userRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository thePostRepository, UserRepository theUserRepository) {
		postRepository = thePostRepository;
		userRepository = theUserRepository;
	}
	
	@Override
	public Post findById(int postId) {
		return postRepository.findById(postId);
	}

	private void validatePageNumber(int page) {
		if (page < 0)
			throw new BadRequestException("Page number cannot be less than zero.");
	}
	
	// Helper method to convert Page<Post> to PagedResponse<PostResponse>
	private PagedResponse<PostResponse> convert(Page<Post> posts) {
		List<PostResponse> postResponses = new LinkedList<>();
		
		for (Post p : posts) {
			User user = p.getUser();
			UserSummary createdBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			postResponses.add(new PostResponse(p.getId(), p.getImage(), p.getCaption(), 
					createdBy, p.getPostTime(), p.getLikes().size(), p.getComments().size()));
		}
		
		return new PagedResponse<>(postResponses, posts.getNumber(), 
				posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
	}
	
	@Override
	public PagedResponse<PostResponse> findByUsernamePage(String username, int page, int size) {
		validatePageNumber(page);
		
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Post> posts = postRepository.findByUsernamePage(username, pageable);
		
		if (posts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(),
            		posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
        }
		
		return convert(posts);
	}
	
	@Override
	public PagedResponse<PostResponse> findFollowingPosts(String username, int page, int size) {
		validatePageNumber(page);
		
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		Pageable pageable = PageRequest.of(page, size);
		
		List<User> following = userRepository.getUserFollowing(username);
		following.add(user);
		List<Post> followingPosts = new LinkedList<>();
		
		for (User u : following) {
			followingPosts.addAll(postRepository.findByUsername(u.getUsername()));
		}
		
		followingPosts.sort((Post p1, Post p2) -> p2.getPostTime().compareTo(p1.getPostTime()));
		
		// PageImpl workaround, pagination won't work without it
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > followingPosts.size() ? followingPosts.size() : (start + pageable.getPageSize());
		
		Page<Post> posts = new PageImpl<Post>(followingPosts.subList(start, end), pageable, followingPosts.size());
		
		return convert(posts);
	}
	
	@Override
	public int countByUsername(String username) {
		return userRepository.findByUsername(username).getPosts().size();
	}
	
	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deleteById(int postId) {
		postRepository.deleteById(postId);
	}


}

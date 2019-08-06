package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
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
	
	// Helper method to convert List<Post> to List<PostResponse>
	private List<PostResponse> convert(List<Post> posts) {
		List<PostResponse> postResponses = new LinkedList<>();
		
		for (Post p : posts) {
			User user = p.getUser();
			UserSummary createdBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			postResponses.add(new PostResponse(p.getId(), p.getImage(), p.getCaption(), 
					createdBy, p.getPostTime(), p.getLikes().size(), p.getComments().size()));
		}
		
		return postResponses;
	}
	
	@Override
	public Post findById(int postId) {
		return postRepository.findById(postId);
	}

	@Override
	public List<PostResponse> findAll() {
		List<Post> posts = postRepository.findAll();
		return convert(posts);
	}

	@Override
	public List<PostResponse> findByUsername(String username) {
		List<Post> posts =  postRepository.findByUsername(username);
		return convert(posts);
	}
	
	@Override
	public List<PostResponse> findFollowingPosts(String username) {
		List<User> following = userRepository.getUserFollowing(username);
		
		List<Post> followingPosts = new LinkedList<>();
		
		for (User u : following) {
			followingPosts.addAll(postRepository.findByUsername(u.getUsername()));
		}
		
		followingPosts.sort((Post p1, Post p2) -> p2.getPostTime().compareTo(p1.getPostTime()));
		
		return convert(followingPosts);
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

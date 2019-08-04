package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
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

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}
	
	@Override
	public List<Post> findByUserId(int userId) {
		return postRepository.findByUserId(userId);
	}

	@Override
	public List<Post> findByUsername(String username) {
		return postRepository.findByUsername(username);
	}
	
	@Override
	public List<Post> findFollowingPosts(String username) {
		List<User> following = userRepository.getUserFollowing(username);
		
		List<Post> followingPosts = new LinkedList<>();
		
		for (User u : following) {
			followingPosts.addAll(postRepository.findByUsername(u.getUsername()));
		}
		
		followingPosts.sort((Post p1, Post p2) -> p2.getPostTime().compareTo(p1.getPostTime()));
		
		return followingPosts;
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

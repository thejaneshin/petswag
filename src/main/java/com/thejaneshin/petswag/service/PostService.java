package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.Post;

public interface PostService {
	public Post findById(int postId);
	
	public List<Post> findAll();
	
	public List<Post> findByUserId(int userId);
	
	public List<Post> findByUsername(String username);
	
	public List<Post> findFollowingPosts(String username);
	
	public Post save(Post post);
	
	public void deleteById(int postId);
}

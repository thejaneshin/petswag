package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.payload.PostResponse;

public interface PostService {
	public Post findById(int postId);
	
	public List<PostResponse> findAll();
	
	public List<PostResponse> findByUsername(String username);
	
	public List<PostResponse> findFollowingPosts(String username);
	
	public int countByUsername(String username);
	
	public Post save(Post post);
	
	public void deleteById(int postId);
}

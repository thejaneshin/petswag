package com.thejaneshin.petswag.service;

import java.util.List;
import java.util.Optional;

import com.thejaneshin.petswag.model.Post;

public interface PostService {
	public Optional<Post> findById(int postId);
	
	public List<Post> findByUserId(int userId);
	
	public Post save(Post post);
	
	public void deleteById(int postId);
}

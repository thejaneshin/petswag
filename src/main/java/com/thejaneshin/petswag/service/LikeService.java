package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.Like;

public interface LikeService {
	public Like findById(int likeId);

	public List<Like> findByUserId(int userId);
	
	public List<Like> findByUsername(String username);
	
	public List<Like> findByPostId(int postId);
	
	public Like save(Like like);
	
	public void deleteById(int likeId);
}

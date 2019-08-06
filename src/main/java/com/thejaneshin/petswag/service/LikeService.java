package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.payload.LikeResponse;

public interface LikeService {
	public Like findById(int likeId);

	public List<Like> findByUserId(int userId);
	
	public List<Like> findByUsername(String username);
	
	public List<LikeResponse> findByPostId(int postId);
	
	public Like findByPostIdAndUsername(int postId, String username);
	
	public Like save(Like like);
	
	public void deleteById(int likeId);
}

package com.thejaneshin.petswag.service;

import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.payload.LikeResponse;
import com.thejaneshin.petswag.payload.PagedResponse;

public interface LikeService {
	public Like findById(int likeId);
	
	public PagedResponse<LikeResponse> findByPostId(int postId, int page, int size);
	
	public Like findByPostIdAndUsername(int postId, String username);
	
	public Like save(Like like);
	
	public void deleteById(int likeId);
}

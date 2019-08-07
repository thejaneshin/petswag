package com.thejaneshin.petswag.service;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.PostResponse;

public interface PostService {
	public Post findById(int postId);
	
	public PagedResponse<PostResponse> findByUsernamePage(String username, int page, int size);
	
	public PagedResponse<PostResponse> findFollowingPosts(String username, int page, int size);
	
	public int countByUsername(String username);
	
	public Post save(Post post);
	
	public void deleteById(int postId);
}

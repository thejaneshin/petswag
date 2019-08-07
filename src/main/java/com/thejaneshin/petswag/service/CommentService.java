package com.thejaneshin.petswag.service;

import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.payload.CommentResponse;
import com.thejaneshin.petswag.payload.PagedResponse;

public interface CommentService {
	public Comment findById(int commentId);
	
	public PagedResponse<CommentResponse> findByPostId(int postId, int page, int size);
	
	public Comment save(Comment comment);
	
	public void deleteById(int commentId);
}

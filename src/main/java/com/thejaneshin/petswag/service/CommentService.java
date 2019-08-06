package com.thejaneshin.petswag.service;

import java.util.List;
import java.util.Optional;

import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.payload.CommentResponse;

public interface CommentService {
	public Optional<Comment> findById(int commentId);
	
	public List<Comment> findByUserId(int userId);
	
	public List<CommentResponse> findByPostId(int postId);
	
	public Comment save(Comment comment);
	
	public void deleteById(int commentId);
}

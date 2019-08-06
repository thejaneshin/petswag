package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.CommentResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	public CommentRepository commentRepository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository theCommentRepository) {
		commentRepository = theCommentRepository;
	}
	
	@Override
	public Optional<Comment> findById(int commentId) {
		return commentRepository.findById(commentId);
	}

	@Override
	public List<Comment> findByUserId(int userId) {
		return commentRepository.findByUserId(userId);
	}

	@Override
	public List<CommentResponse> findByPostId(int postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		List<CommentResponse> commentResponses = new LinkedList<>();
		
		for (Comment c : comments) {
			User user = c.getUser(); 
			UserSummary commentedBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			commentResponses.add(new CommentResponse(c.getId(), c.getText(), commentedBy, c.getCommentTime()));
		}
		
		return commentResponses;
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void deleteById(int commentId) {
		commentRepository.deleteById(commentId);
	}

}

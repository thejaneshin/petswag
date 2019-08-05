package com.thejaneshin.petswag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.repository.CommentRepository;
import com.thejaneshin.petswag.service.CommentService;

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
	public List<Comment> findByPostId(int postId) {
		return commentRepository.findByPostId(postId);
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

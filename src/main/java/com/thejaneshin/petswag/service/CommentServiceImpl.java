package com.thejaneshin.petswag.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.exception.BadRequestException;
import com.thejaneshin.petswag.exception.ResourceNotFoundException;
import com.thejaneshin.petswag.model.Comment;
import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.CommentResponse;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.CommentRepository;
import com.thejaneshin.petswag.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	public CommentRepository commentRepository;
	public PostRepository postRepository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository theCommentRepository, PostRepository thePostRepository) {
		commentRepository = theCommentRepository;
		postRepository = thePostRepository;
	}
	
	@Override
	public Comment findById(int commentId) {
		return commentRepository.findById(commentId);
	}

	private void validatePageNumber(int page) {
		if (page < 0)
			throw new BadRequestException("Page number cannot be less than zero.");
	}
	
	private PagedResponse<CommentResponse> convert(Page<Comment> comments) {
		List<CommentResponse> commentResponses = new LinkedList<>();
		
		for (Comment c : comments) {
			User user = c.getUser();
			UserSummary commentedBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			commentResponses.add(new CommentResponse(c.getId(), c.getText(), commentedBy, c.getCommentTime()));
		}
		
		return new PagedResponse<>(commentResponses, comments.getNumber(), 
				comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
	}
	
	@Override
	public PagedResponse<CommentResponse> findByPostId(int postId, int page, int size) {
		validatePageNumber(page);
		
		Post post = postRepository.findById(postId);
		
		if (post == null)
			throw new ResourceNotFoundException("Post", "postId", postId);
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
		
		if (comments.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), comments.getNumber(),
					comments.getSize(), comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
		}
		
		return convert(comments);
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

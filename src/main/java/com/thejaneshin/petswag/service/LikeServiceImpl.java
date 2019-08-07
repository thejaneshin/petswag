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
import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.LikeResponse;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.LikeRepository;
import com.thejaneshin.petswag.repository.PostRepository;

@Service
public class LikeServiceImpl implements LikeService {
	LikeRepository likeRepository;
	public PostRepository postRepository;
	
	@Autowired
	public LikeServiceImpl(LikeRepository theLikeRepository, PostRepository thePostRepository) {
		likeRepository = theLikeRepository;
		postRepository = thePostRepository;
	}
	
	@Override
	public Like findById(int likeId) {
		return likeRepository.findById(likeId);
	}
	
	private void validatePageNumber(int page) {
		if (page < 0)
			throw new BadRequestException("Page number cannot be less than zero.");
	}
	
	private PagedResponse<LikeResponse> convert(Page<Like> likes) {
		List<LikeResponse> likeResponses = new LinkedList<>();
		
		for (Like l : likes) {
			User user = l.getUser();
			UserSummary likedBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			likeResponses.add(new LikeResponse(l.getId(), likedBy, l.getLikeTime()));
		}
		
		return new PagedResponse<>(likeResponses, likes.getNumber(), 
				likes.getSize(), likes.getTotalElements(), likes.getTotalPages(), likes.isLast());
	}
	
	@Override
	public PagedResponse<LikeResponse> findByPostId(int postId, int page, int size) {
		validatePageNumber(page);
		
		Post post = postRepository.findById(postId);
		
		if (post == null)
			throw new ResourceNotFoundException("Post", "postId", postId);
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Like> likes = likeRepository.findByPostId(postId, pageable);
		
		if (likes.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), likes.getNumber(),
            		likes.getSize(), likes.getTotalElements(), likes.getTotalPages(), likes.isLast());
        }
		
		return convert(likes);
	}

	@Override
	public Like findByPostIdAndUsername(int postId, String username) {
		return likeRepository.findByPostIdAndUsername(postId, username);
	}
	
	@Override
	public Like save(Like like) {
		return likeRepository.save(like);
	}

	@Override
	public void deleteById(int likeId) {
		likeRepository.deleteById(likeId);
	}

}

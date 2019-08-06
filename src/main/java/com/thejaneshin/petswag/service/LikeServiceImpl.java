package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.LikeResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService {
	LikeRepository likeRepository;
	
	@Autowired
	public LikeServiceImpl(LikeRepository theLikeRepository) {
		likeRepository = theLikeRepository;
	}
	
	@Override
	public Like findById(int likeId) {
		return likeRepository.findById(likeId);
	}

	@Override
	public List<Like> findByUserId(int userId) {
		return likeRepository.findByUserId(userId);
	}

	@Override
	public List<Like> findByUsername(String username) {
		return likeRepository.findByUsername(username);
	}
	
	@Override
	public List<LikeResponse> findByPostId(int postId) {
		List<Like> likes = likeRepository.findByPostId(postId);
		List<LikeResponse> likeResponses = new LinkedList<>();
		
		for (Like l : likes) {
			User user = l.getUser();
			UserSummary likedBy = new UserSummary(user.getId(), user.getUsername(), user.getAvatar());
			likeResponses.add(new LikeResponse(l.getId(), likedBy, l.getLikeTime()));
		}
		
		return likeResponses;
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

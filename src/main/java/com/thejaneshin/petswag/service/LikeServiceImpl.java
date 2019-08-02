package com.thejaneshin.petswag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Like;
import com.thejaneshin.petswag.repository.LikeRepository;

@Service
public class LikeServiceImpl implements LikeService {
	LikeRepository likeRepository;
	
	@Autowired
	public LikeServiceImpl(LikeRepository theLikeRepository) {
		likeRepository = theLikeRepository;
	}
	
	@Override
	public Optional<Like> findById(int likeId) {
		return likeRepository.findById(likeId);
	}

	@Override
	public List<Like> findByUserId(int userId) {
		return likeRepository.findByUserId(userId);
	}

	@Override
	public List<Like> findByPostId(int postId) {
		return likeRepository.findByPostId(postId);
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

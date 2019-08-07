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
import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.UserSummary;
import com.thejaneshin.petswag.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	public UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}

	@Override
	public User findByUsernameOrEmail(String username, String email) {
		return userRepository.findByUsernameOrEmail(username, email);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId);
	}

	private void validatePageNumber(int page) {
		if (page < 0)
			throw new BadRequestException("Page number cannot be less than zero.");
	}
	
	@Override
	public PagedResponse<UserSummary> getUserFollowers(String username, int page, int size) {
		validatePageNumber(page);
		
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		Pageable pageable = PageRequest.of(page, size);
		Page<User> followers = userRepository.getUserFollowersPage(username, pageable);
		
		if (followers.getNumberOfElements() == 0)
			return new PagedResponse<>(Collections.emptyList(), followers.getNumber(),
                    followers.getSize(), followers.getTotalElements(), followers.getTotalPages(), followers.isLast());
		
		List<UserSummary> followerSummaries = new LinkedList<>(); 
		
		for (User f : followers) {
			followerSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return new PagedResponse<>(followerSummaries, followers.getNumber(), 
				followers.getSize(), followers.getTotalElements(), followers.getTotalPages(), followers.isLast());
	}
	
	@Override
	public PagedResponse<UserSummary> getUserFollowing(String username, int page, int size) {
		validatePageNumber(page);
		
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		Pageable pageable = PageRequest.of(page, size);
		Page<User> following = userRepository.getUserFollowingPage(username, pageable);
		
		if (following.getNumberOfElements() == 0)
			return new PagedResponse<>(Collections.emptyList(), following.getNumber(),
					following.getSize(), following.getTotalElements(), following.getTotalPages(), following.isLast());
		
		List<UserSummary> followingSummaries = new LinkedList<>(); 
		
		for (User f : following) {
			followingSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return new PagedResponse<>(followingSummaries, following.getNumber(), 
				following.getSize(), following.getTotalElements(), following.getTotalPages(), following.isLast());	
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public int countFollowers(String username) {	
		return userRepository.getUserFollowers(username).size();
	}
	
	@Override
	public int countFollowing(String username) {	
		return userRepository.getUserFollowing(username).size();
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}

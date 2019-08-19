package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

	@Override
	public List<String> getUserFollowingNames(String username) {
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		List<User> following = userRepository.getUserFollowing(username);
		
		List<String> followingNames = new LinkedList<>();
		
		for (User f : following) {
			followingNames.add(f.getUsername());
		}
		
		return followingNames;
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
		List<User> followers = userRepository.getUserFollowers(username);
		
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > followers.size() ? followers.size() : (start + pageable.getPageSize());
		
		Page<User> followersPage = new PageImpl<User>(followers.subList(start, end), pageable, followers.size());
		
		List<UserSummary> followerSummaries = new LinkedList<>(); 
		
		for (User f : followersPage) {
			followerSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return new PagedResponse<>(followerSummaries, followersPage.getNumber(), 
				followersPage.getSize(), followersPage.getTotalElements(), followersPage.getTotalPages(), followersPage.isLast());
	}
	
	@Override
	public PagedResponse<UserSummary> getUserFollowing(String username, int page, int size) {
		validatePageNumber(page);
		
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new ResourceNotFoundException("User", "username", username);
		
		Pageable pageable = PageRequest.of(page, size);
		List<User> following = userRepository.getUserFollowing(username);
		
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > following.size() ? following.size() : (start + pageable.getPageSize());
		
		Page<User> followingPage = new PageImpl<User>(following.subList(start, end), pageable, following.size());
		
		List<UserSummary> followingSummaries = new LinkedList<>(); 
		
		for (User f : followingPage) {
			followingSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return new PagedResponse<>(followingSummaries, followingPage.getNumber(), 
				followingPage.getSize(), followingPage.getTotalElements(), followingPage.getTotalPages(), followingPage.isLast());	
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

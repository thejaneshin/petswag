package com.thejaneshin.petswag.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.User;
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
	public List<UserSummary> getUserFollowers(String username) {
		List<User> followers = userRepository.getUserFollowers(username);
		
		List<UserSummary> followerSummaries = new LinkedList<>(); 
		
		for (User f : followers) {
			followerSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return followerSummaries;
	}
	
	@Override
	public List<UserSummary> getUserFollowing(String username) {
		List<User> following = userRepository.getUserFollowing(username);
		
		List<UserSummary> followingSummaries = new LinkedList<>(); 
		
		for (User f : following) {
			followingSummaries.add(new UserSummary(f.getId(), f.getUsername(), 
				f.getAvatar()));
		}
		
		return followingSummaries;
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public int countFollowing(String username) {
		return getUserFollowing(username).size();
	}
	
	@Override
	public int countFollowers(String username) {
		return getUserFollowers(username).size();
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

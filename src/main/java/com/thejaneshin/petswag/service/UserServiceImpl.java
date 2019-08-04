package com.thejaneshin.petswag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.User;
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
	public List<User> getUserFollowers(String username) {
		return userRepository.getUserFollowers(username);
	}
	
	@Override
	public List<User> getUserFollowing(String username) {
		return userRepository.getUserFollowing(username);
	}
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
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

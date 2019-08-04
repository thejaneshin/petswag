package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.User;

public interface UserService {
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);
	
	public User findById(int userId);
	
	public List<User> getUserFollowers(String username);
	
	public List<User> getUserFollowing(String username);
	
	public User save(User user);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

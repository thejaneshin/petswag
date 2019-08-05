package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.UserSummary;

public interface UserService {
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);
	
	public User findById(int userId);
	
	public List<UserSummary> getUserFollowers(String username);
	
	public List<UserSummary> getUserFollowing(String username);
	
	public User save(User user);
	
	public int countFollowing(String username);
	
	public int countFollowers(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

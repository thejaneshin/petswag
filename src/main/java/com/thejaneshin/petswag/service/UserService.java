package com.thejaneshin.petswag.service;

import java.util.List;

import com.thejaneshin.petswag.model.User;
import com.thejaneshin.petswag.payload.PagedResponse;
import com.thejaneshin.petswag.payload.UserSummary;

public interface UserService {
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);
	
	public User findById(int userId);
	
	public List<String> getUserFollowingNames(String username);
	
	public PagedResponse<UserSummary> getUserFollowers(String username, int page, int size);
	
	public PagedResponse<UserSummary> getUserFollowing(String username, int page, int size);
	
	public User save(User user);
	
	public int countFollowing(String username);
	
	public int countFollowers(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

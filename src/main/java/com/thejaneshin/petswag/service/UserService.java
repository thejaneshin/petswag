package com.thejaneshin.petswag.service;

import java.util.Optional;

import com.thejaneshin.petswag.model.User;

public interface UserService {
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByUsernameOrEmail(String username, String email);
	
	public Optional<User> findById(int userId);
	
	public User save(User user);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

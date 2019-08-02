package com.thejaneshin.petswag.service;

import java.util.Optional;

import com.thejaneshin.petswag.model.User;

public interface UserService {
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findById(int userId);
	
	public void saveUser(User user);
}

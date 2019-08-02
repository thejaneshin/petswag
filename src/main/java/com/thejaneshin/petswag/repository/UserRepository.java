package com.thejaneshin.petswag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thejaneshin.petswag.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findById(int userId);
}

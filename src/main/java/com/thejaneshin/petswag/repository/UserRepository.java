package com.thejaneshin.petswag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);
	
	public User findById(int userId);
	
	@Query("Select u.followerList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public List<User> getUserFollowers(String username);
	
	@Query("Select u.followingList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public List<User> getUserFollowing(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

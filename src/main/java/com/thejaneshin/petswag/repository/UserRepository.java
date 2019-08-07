package com.thejaneshin.petswag.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	
	public User findByUsernameOrEmail(String username, String email);
	
	public User findById(int userId);
	
	@Query("SELECT u.followerList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public List<User> getUserFollowers(String username);
	
	@Query("SELECT u.followingList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public List<User> getUserFollowing(String username);
	
	@Query("SELECT u.followerList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public Page<User> getUserFollowersPage(String username, Pageable pageable);
	
	@Query("SELECT u.followingList FROM User u WHERE u.username=:username ORDER BY u.username ASC")
	public Page<User> getUserFollowingPage(String username, Pageable pageable);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}

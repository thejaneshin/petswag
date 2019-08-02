package com.thejaneshin.petswag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	public Optional<Post> findById(int postId);
	
	@Query("SELECT p FROM Post p WHERE p.user.id=:userId")
	public List<Post> findByUserId(int userId);
	
}

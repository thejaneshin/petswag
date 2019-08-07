package com.thejaneshin.petswag.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	public Post findById(int postId);
	
	@Query("SELECT p FROM Post p WHERE p.user.username=:username ORDER BY p.postTime DESC")
	public List<Post> findByUsername(String username);
	
	@Query("SELECT p FROM Post p WHERE p.user.username=:username ORDER BY p.postTime DESC")
	public Page<Post> findByUsernamePage(String username, Pageable pageable);
	
}

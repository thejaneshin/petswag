package com.thejaneshin.petswag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	public Like findById(int likeId);
	
	@Query("SELECT l FROM Like l WHERE l.user.id=:userId")
	public List<Like> findByUserId(int userId);
	
	@Query("SELECT l FROM Like l WHERE l.user.username=:username")
	public List<Like> findByUsername(String username);
	
	@Query("SELECT l FROM Like l WHERE l.post.id=:postId ORDER BY l.likeTime DESC")
	public List<Like> findByPostId(int postId);
	
	@Query("SELECT l FROM Like l WHERE l.post.id=:postId AND l.user.username=:username")
	public Like findByPostIdAndUsername(int postId, String username);
}

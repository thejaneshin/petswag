package com.thejaneshin.petswag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	public Optional<Like> findById(int likeId);
	
	@Query("SELECT l FROM Like l WHERE l.user.id=:userId")
	public List<Like> findByUserId(int userId);
	
	@Query("SELECT l FROM Like l WHERE l.post.id=:postId")
	public List<Like> findByPostId(int postId);
}

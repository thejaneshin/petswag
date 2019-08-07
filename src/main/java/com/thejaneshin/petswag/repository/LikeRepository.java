package com.thejaneshin.petswag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	public Like findById(int likeId);
	
	@Query("SELECT l FROM Like l WHERE l.post.id=:postId ORDER BY l.likeTime DESC")
	public Page<Like> findByPostId(int postId, Pageable pageable);
	
	@Query("SELECT l FROM Like l WHERE l.post.id=:postId AND l.user.username=:username")
	public Like findByPostIdAndUsername(int postId, String username);
}

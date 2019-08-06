package com.thejaneshin.petswag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	public Optional<Comment> findById(int commentId);
	
	@Query("SELECT c FROM Comment c WHERE c.user.id=:userId ORDER BY c.commentTime DESC")
	public List<Comment> findByUserId(int userId);
	
	@Query("SELECT c FROM Comment c WHERE c.post.id=:postId ORDER BY c.commentTime DESC")
	public List<Comment> findByPostId(int postId);
}

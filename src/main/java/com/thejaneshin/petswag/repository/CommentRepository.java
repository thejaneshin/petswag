package com.thejaneshin.petswag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thejaneshin.petswag.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	public Comment findById(int commentId);
	
	@Query("SELECT c FROM Comment c WHERE c.post.id=:postId ORDER BY c.commentTime DESC")
	public Page<Comment> findByPostId(int postId, Pageable pageable);
}

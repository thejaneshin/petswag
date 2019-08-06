package com.thejaneshin.petswag.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentResponse {
	private int id;
	private String text;
	private UserSummary commentedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime commentTime;
	
	public CommentResponse() {
		
	}

	public CommentResponse(int id, String text, UserSummary commentedBy, LocalDateTime commentTime) {
		this.id = id;
		this.text = text;
		this.commentedBy = commentedBy;
		this.commentTime = commentTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UserSummary getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(UserSummary commentedBy) {
		this.commentedBy = commentedBy;
	}

	public LocalDateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(LocalDateTime commentTime) {
		this.commentTime = commentTime;
	}
	
	
}

package com.thejaneshin.petswag.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PostResponse {
	private int id;
	private String image;
	private String caption;
	private UserSummary createdBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime postTime;
	private int likeCount;
	private int commentCount;
	
	public PostResponse() {
		
	}

	public PostResponse(int id, String image, String caption, UserSummary createdBy, LocalDateTime postTime,
			int likeCount, int commentCount) {
		this.id = id;
		this.image = image;
		this.caption = caption;
		this.createdBy = createdBy;
		this.postTime = postTime;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public UserSummary getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getPostTime() {
		return postTime;
	}

	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
}

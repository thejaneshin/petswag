package com.thejaneshin.petswag.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LikeResponse {
	int id;
	private UserSummary likedBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime likeTime;
	
	public LikeResponse() {
		
	}

	public LikeResponse(int id, UserSummary likedBy, LocalDateTime likeTime) {
		this.id = id;
		this.likedBy = likedBy;
		this.likeTime = likeTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserSummary getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(UserSummary likedBy) {
		this.likedBy = likedBy;
	}

	public LocalDateTime getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(LocalDateTime likeTime) {
		this.likeTime = likeTime;
	}
	
	
	
}

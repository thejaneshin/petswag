package com.thejaneshin.petswag.payload;

public class UserProfile {
	private int id;
	private String username;
	private String name;
	private String bio;
	private String avatar;
	private int postCount;
	private int followingCount;
	private int followerCount;
	
	public UserProfile() {
		
	}

	public UserProfile(int id, String username, String name, String bio, String avatar, int postCount,
			int followingCount, int followerCount) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.bio = bio;
		this.avatar = avatar;
		this.postCount = postCount;
		this.followingCount = followingCount;
		this.followerCount = followerCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	
	
}

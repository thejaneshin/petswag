package com.thejaneshin.petswag.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@Column(name="bio")
	private String bio;
	
	@Column(name="avatar")
	private String avatar;
	
	@Column(name="enabled")
	private int enabled;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Post> posts;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Comment> comments;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="follow", 
			joinColumns=@JoinColumn(name="follower_id"), 
			inverseJoinColumns=@JoinColumn(name="followed_id"))
	private Set<User> followsList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="follow", 
			joinColumns=@JoinColumn(name="followed_id"), 
			inverseJoinColumns=@JoinColumn(name="follower_id"))
	private Set<User> followerList;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<User> getFollowsList() {
		return followsList;
	}

	public void setFollowsList(Set<User> followsList) {
		this.followsList = followsList;
	}

	public Set<User> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(Set<User> followerList) {
		this.followerList = followerList;
	}

}

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank
	@Size(max=50)
	@Column(name="username")
	private String username;
	
	@NotBlank
	@Size(max=68)
	@Column(name="password")
	private String password;
	
	@NotBlank
	@Size(max=50)
	@Column(name="email")
	private String email;
	
	@NotBlank
	@Size(max=45)
	@Column(name="name")
	private String name;
	
	@Size(max=255)
	@Column(name="bio")
	private String bio;
	
	@Size(max=255)
	@Column(name="avatar")
	private String avatar;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	@JsonManagedReference
	private Set<Post> posts;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="user",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Comment> comments;
	
	@JsonIgnoreProperties({"password", "email", "bio", "posts", "likes", "comments", "followingList", "followerList", "roles"})
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="follow", 
			joinColumns=@JoinColumn(name="follower_id"), 
			inverseJoinColumns=@JoinColumn(name="followed_id"))
	private Set<User> followingList;
	
	@JsonIgnoreProperties({"password", "email", "bio", "posts", "likes", "comments", "followingList", "followerList", "roles"})
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="follow", 
			joinColumns=@JoinColumn(name="followed_id"), 
			inverseJoinColumns=@JoinColumn(name="follower_id"))
	private Set<User> followerList;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="user_role", 
	joinColumns=@JoinColumn(name="user_id"), 
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles;
	
	public User() {
		
	}
	
	public User(String username, String password, String email, String name) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
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

	public Set<User> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(Set<User> followsList) {
		this.followingList = followsList;
	}

	public Set<User> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(Set<User> followerList) {
		this.followerList = followerList;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}

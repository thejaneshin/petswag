package com.thejaneshin.petswag.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank
	@Size(max=255)
	@Column(name="image")
	private String image;
	
	@Size(max=50)
	@Column(name="caption")
	private String caption;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="post_time")
	private LocalDateTime postTime;

	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	@JsonBackReference
	private User user;
	
	@OneToMany(mappedBy="post",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="post",
			fetch=FetchType.LAZY,
			cascade=CascadeType.ALL)
	private Set<Comment> comments;
	
	public Post() {
		
	}
	
	public Post(String image, String caption, LocalDateTime postTime, User user) {
		this.image = image;
		this.caption = caption;
		this.postTime = postTime;
		this.user = user;
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

	public LocalDateTime getPostTime() {
		return postTime;
	}

	public void setPostTime(LocalDateTime postTime) {
		this.postTime = postTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	
}
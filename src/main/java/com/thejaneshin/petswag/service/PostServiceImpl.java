package com.thejaneshin.petswag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thejaneshin.petswag.model.Post;
import com.thejaneshin.petswag.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	public PostRepository postRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository thePostRepository) {
		postRepository = thePostRepository;
	}
	
	@Override
	public Optional<Post> findById(int postId) {
		return postRepository.findById(postId);
	}

	@Override
	public List<Post> findByUserId(int userId) {
		return postRepository.findByUserId(userId);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public void deleteById(int postId) {
		postRepository.deleteById(postId);
	}

}

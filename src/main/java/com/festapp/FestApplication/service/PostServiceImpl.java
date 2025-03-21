package com.festapp.FestApplication.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.dto.PostDTO;
import com.festapp.FestApplication.models.Comment;
import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.CommentRepository;
import com.festapp.FestApplication.repository.PostRepository;
import com.festapp.FestApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
        this.commentRepository = commentRepository;

	}

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> getAllPostsByUser(Long userId) {
		return postRepository.findByUserId(userId);
	}

	@Override
	public List<Post> getAllPostsByUsers(List<User> users) {
		List<Post> allPosts = new ArrayList<>();
		for (User user : users) {
			allPosts.addAll(postRepository.findByUserId(user.getId()));
		}
		return allPosts;
	}

	@Override
	public Post createPost(PostDTO postRequest) {

		User user = userRepository.findById(postRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Post post = new Post();
		post.setContent(postRequest.getContent());
		post.setMediaUrl(postRequest.getMediaUrl());
		post.setTags(postRequest.getTags());
		post.setUser(user);

		return postRepository.save(post);
	}

	@Override
	@Transactional
	public Post updatePost(Long userId, Long postId, String newContent) {
		// Pronalazimo post po ID-u
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

		// Proveravamo da li korisnik zaista poseduje taj post
		if (!post.getUser().getId().equals(userId)) {
			throw new RuntimeException("You are not the owner of this post!");
		}

		// Menjamo sadržaj posta
		post.setContent(newContent);
		return postRepository.save(post);
	}
	
	@Override
	@Transactional
	public void deletePost(Long userId, Long postId) {
	    // Pronalazimo post po ID-u
	    Post post = postRepository.findById(postId)
	            .orElseThrow(() -> new RuntimeException("Post not found"));

	    // Proveravamo da li korisnik zaista poseduje taj post
	    if (!post.getUser().getId().equals(userId)) {
	        throw new RuntimeException("You are not the owner of this post!");
	    }

	    // Pronalazimo sve komentare vezane za post
	    List<Comment> comments = commentRepository.findByPostId(postId);

	    // Ako korisnik nije vlasnik komentara, on ne može da izbriše post
	    // Brisanje komentara može biti odvojeno, ali ovde ćemo to obaviti odmah
	    if (!comments.isEmpty()) {
	        // Brišemo sve komentare povezane sa postom
	        commentRepository.deleteAll(comments);
	    }

	    // Brišemo sam post
	    postRepository.delete(post);
	}
}

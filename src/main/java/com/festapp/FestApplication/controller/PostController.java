package com.festapp.FestApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.festapp.FestApplication.dto.PostDTO;
import com.festapp.FestApplication.dto.PostListDTO;
import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.service.FollowerService;
import com.festapp.FestApplication.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FollowerService followerService;

	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() {
		List<Post> posts = postService.getAllPosts();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<PostListDTO>> getAllPostsByUser(@PathVariable Long userId) {
		List<PostListDTO> posts = (List<PostListDTO>) postService.getAllPostsByUser(userId);
		if (posts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/{userId}/followed-posts")
	public ResponseEntity<List<PostListDTO>> getAllPostsFromFollowedUsers(@PathVariable Long userId) {
		List<User> followedUsers = followerService.getAllFollowedUsers(userId);
		if (followedUsers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<PostListDTO> postsFromFollowedUsers = postService.getAllPostsByUsers(followedUsers);
		return new ResponseEntity<>(postsFromFollowedUsers, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@RequestBody PostDTO post) {
		try {
			Post newPost = postService.createPost(post);
			return new ResponseEntity<>(newPost, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Post> updatePost(@RequestParam Long userId, @PathVariable Long postId,
			@RequestBody PostDTO postDTO) {
		return ResponseEntity.ok(postService.updatePost(userId, postId, postDTO.getContent()));
	}

	// API za brisanje posta
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePost(@RequestParam Long userId, @PathVariable Long postId) {
		postService.deletePost(userId, postId);
		return ResponseEntity.ok("Post deleted successfully");
	}

}
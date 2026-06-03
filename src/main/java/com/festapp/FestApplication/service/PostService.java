package com.festapp.FestApplication.service;

import java.util.List;

import com.festapp.FestApplication.dto.PostDTO;
import com.festapp.FestApplication.dto.PostListDTO;
import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.User;

public interface PostService {
	
    Post getPostById(Long id);

	List<Post> getAllPosts();

	List<PostListDTO> getAllPostsByUser(Long userId);

	List<PostListDTO> getAllPostsByUsers(List<User> users);

	long countPostsByUser(Long userId);

	Post createPost(PostDTO post);

	Post updatePost(Long userId, Long postId, String newContent);

	void deletePost(Long userId, Long postId);

	void deletePost(Long postId);

}

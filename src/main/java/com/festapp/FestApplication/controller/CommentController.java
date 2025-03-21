package com.festapp.FestApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.festapp.FestApplication.dto.CommentDTO;
import com.festapp.FestApplication.models.Comment;
import com.festapp.FestApplication.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/post/{postId}")
	public List<CommentDTO> getCommentsByPost(@PathVariable Long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@PostMapping("/{postId}")
	public ResponseEntity<Comment> createComment(@RequestParam Long userId, @PathVariable Long postId,
			@RequestBody CommentDTO commentDTO) {

		Comment createdComment = commentService.createComment(userId, postId, commentDTO);

		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Comment> updateComment(@RequestParam Long userId, @PathVariable Long commentId,
			@RequestBody CommentDTO commentDTO) {
		Comment updatedComment = commentService.updateComment(userId, commentId, commentDTO.getContent());
		return ResponseEntity.ok(updatedComment);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@RequestParam Long userId, @PathVariable Long commentId) {
		commentService.deleteComment(userId, commentId);
		return ResponseEntity.noContent().build();
	}

}

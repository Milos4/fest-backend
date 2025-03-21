package com.festapp.FestApplication.service;

import java.util.List;

import com.festapp.FestApplication.dto.CommentDTO;
import com.festapp.FestApplication.models.Comment;

public interface CommentService {

	List<CommentDTO> getCommentsByPostId(Long postId);

	Comment createComment(Long userId, Long postId, CommentDTO commentDTO);

	void deleteComment(Long userId, Long commentId);

	Comment updateComment(Long userId, Long commentId, String newContent);
}

package com.festapp.FestApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
    List<Comment> findByPostId(Long postId);


}

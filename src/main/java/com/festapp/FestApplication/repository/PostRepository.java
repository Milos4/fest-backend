package com.festapp.FestApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserId(Long userId);
	
    List<Post> findByUserIdIn(List<Long> userIds);

}

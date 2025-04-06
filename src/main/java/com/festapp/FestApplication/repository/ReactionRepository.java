package com.festapp.FestApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Post;
import com.festapp.FestApplication.models.Reaction;
import com.festapp.FestApplication.models.User;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
	Optional<Reaction> findByUserAndPost(User user, Post post);

}

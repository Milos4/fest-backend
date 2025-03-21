package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}

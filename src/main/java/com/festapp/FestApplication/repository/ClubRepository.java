package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

}

package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.ClubEvent;

@Repository
public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {

}

package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.UserEventSwipe;

@Repository
public interface UserEventSwipeRepository extends JpaRepository<UserEventSwipe, Long> {

}

package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.ClubRequest;

public interface ClubRequestRepository extends JpaRepository<ClubRequest, Long> {

}

package com.festapp.FestApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.Bio;

public interface BioRepository extends JpaRepository<Bio, Long> {
	
    Optional<Bio> findByUserId(Long userId);

}

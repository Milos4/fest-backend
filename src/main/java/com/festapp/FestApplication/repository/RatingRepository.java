package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}

package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.MonthlyPaymentClub;

@Repository
public interface MonthlyPaymentClubRepository extends JpaRepository<MonthlyPaymentClub, Long> {

}

package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}

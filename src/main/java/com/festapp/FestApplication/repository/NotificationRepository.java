package com.festapp.FestApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

	List<Notification> findByUserIdAndIsReadFalse(Long userId);

	long countByUserIdAndIsReadFalse(Long userId);
}

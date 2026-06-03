package com.festapp.FestApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.UserEvent;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    List<UserEvent> findByHostUserId(Long hostUserId);

}

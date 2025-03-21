package com.festapp.FestApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.UserEvent;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

}

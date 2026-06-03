package com.festapp.FestApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.models.UserEvent;
import com.festapp.FestApplication.models.UserEventSwipe;
import com.festapp.FestApplication.models.UserEventSwipe.SwipeStatus;

@Repository
public interface UserEventSwipeRepository extends JpaRepository<UserEventSwipe, Long> {

    Optional<UserEventSwipe> findByUserAndUserEvent(User user, UserEvent userEvent);

    Optional<UserEventSwipe> findByUserAndUserEventAndStatus(User user, UserEvent userEvent, SwipeStatus status);

    List<UserEventSwipe> findByUserEventIdAndStatus(Long userEventId, SwipeStatus status);

    List<UserEventSwipe> findByUserIdAndStatus(Long userId, SwipeStatus status);
}

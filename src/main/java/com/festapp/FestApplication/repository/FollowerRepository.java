package com.festapp.FestApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.festapp.FestApplication.models.Follower;
import com.festapp.FestApplication.models.User;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findFollowingByFollowerId(Long followerId);

    Optional<Follower> findByFollowerAndFollowing(User follower, User following);

    
}

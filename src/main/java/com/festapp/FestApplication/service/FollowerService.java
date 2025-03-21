package com.festapp.FestApplication.service;

import java.util.List;

import com.festapp.FestApplication.models.User;

public interface FollowerService {
	boolean isFollowing(Long followerId, Long followingId);
    boolean toggleFollow(Long followerId, Long followingId);
	List<User> getAllFollowedUsers(Long userId);

}

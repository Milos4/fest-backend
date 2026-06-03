package com.festapp.FestApplication.service;

import java.util.List;
import java.util.Map;

import com.festapp.FestApplication.dto.ChatContactDTO;
import com.festapp.FestApplication.models.Follower;
import com.festapp.FestApplication.models.User;

public interface FollowerService {
	boolean isFollowing(Long followerId, Long followingId);
	boolean hasPendingRequest(Long followerId, Long followingId);
    boolean toggleFollow(Long followerId, Long followingId);
    void acceptFollowRequest(Long followerId, Long followingId);
    void rejectFollowRequest(Long followerId, Long followingId);
    List<Follower> getPendingFollowRequests(Long userId);
    Map<String, Boolean> getFollowStatus(Long currentUserId, Long profileUserId);
    long countFollowers(Long userId);
    long countFollowing(Long userId);
    List<ChatContactDTO> getChatContacts(Long userId);
	List<User> getAllFollowedUsers(Long userId);

}

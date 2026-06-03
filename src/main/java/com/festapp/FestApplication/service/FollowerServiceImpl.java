package com.festapp.FestApplication.service;

import com.festapp.FestApplication.dto.ChatContactDTO;
import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.models.Follower;
import com.festapp.FestApplication.models.Follower.FollowStatus;
import com.festapp.FestApplication.models.Notification;
import com.festapp.FestApplication.models.Notification.NotificationType;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.FollowerRepository;
import com.festapp.FestApplication.repository.NotificationRepository;
import com.festapp.FestApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("User not found"));
        User following = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("User not found"));

        return followerRepository.findByFollowerAndFollowingAndStatus(follower, following, FollowStatus.ACCEPTED)
                .isPresent();
    }

    @Override
    public boolean hasPendingRequest(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("User not found"));
        User following = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("User not found"));

        return followerRepository.findByFollowerAndFollowingAndStatus(follower, following, FollowStatus.PENDING)
                .isPresent();
    }

    @Override
    @Transactional
    public boolean toggleFollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (follower.getId().equals(following.getId())) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        Optional<Follower> existingFollow = followerRepository.findByFollowerAndFollowing(follower, following);

        if (existingFollow.isPresent()) {
            Follower follow = existingFollow.get();
            if (follow.getStatus() == FollowStatus.ACCEPTED) {
                followerRepository.delete(follow);
            }
            return false;
        }

        Follower newFollow = new Follower();
        newFollow.setFollower(follower);
        newFollow.setFollowing(following);
        newFollow.setActionDate(LocalDateTime.now());

        boolean isPrivateProfile = following.getBio() != null && following.getBio().isPrivateProfile();
        if (isPrivateProfile) {
            newFollow.setStatus(FollowStatus.PENDING);
            followerRepository.save(newFollow);

            Notification notification = new Notification(
                    null,
                    following,
                    follower.getUsername() + " requested to follow you!",
                    NotificationType.FOLLOW_REQUEST,
                    LocalDateTime.now(),
                    false
            );
            notificationRepository.save(notification);
            return false;
        }

        newFollow.setStatus(FollowStatus.ACCEPTED);
        followerRepository.save(newFollow);

        Notification notification = new Notification(
                null,
                following,
                follower.getUsername() + " followed you!",
                NotificationType.FOLLOW,
                LocalDateTime.now(),
                false
        );
        notificationRepository.save(notification);

        return true;
    }

    @Override
    @Transactional
    public void acceptFollowRequest(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following user not found"));

        Follower followRequest = followerRepository
                .findByFollowerAndFollowingAndStatus(follower, following, FollowStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Follow request not found"));

        followRequest.setStatus(FollowStatus.ACCEPTED);
        followRequest.setActionDate(LocalDateTime.now());
        followerRepository.save(followRequest);

        Notification notification = new Notification(
                null,
                follower,
                following.getUsername() + " accepted your follow request!",
                NotificationType.FOLLOW_ACCEPTED,
                LocalDateTime.now(),
                false
        );
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void rejectFollowRequest(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Following user not found"));

        Follower followRequest = followerRepository
                .findByFollowerAndFollowingAndStatus(follower, following, FollowStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Follow request not found"));

        followerRepository.delete(followRequest);
    }

    @Override
    public List<Follower> getPendingFollowRequests(Long userId) {
        return followerRepository.findByFollowingIdAndStatus(userId, FollowStatus.PENDING);
    }

    @Override
    public Map<String, Boolean> getFollowStatus(Long currentUserId, Long profileUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        User profileUser = userRepository.findById(profileUserId)
                .orElseThrow(() -> new RuntimeException("Profile user not found"));

        boolean isOwnProfile = currentUser.getId().equals(profileUser.getId());
        boolean isPrivateProfile = profileUser.getBio() != null && profileUser.getBio().isPrivateProfile();
        boolean isFollowing = followerRepository
                .findByFollowerAndFollowingAndStatus(currentUser, profileUser, FollowStatus.ACCEPTED)
                .isPresent();
        boolean hasPendingRequest = followerRepository
                .findByFollowerAndFollowingAndStatus(currentUser, profileUser, FollowStatus.PENDING)
                .isPresent();
        boolean canViewProfile = isOwnProfile || !isPrivateProfile || isFollowing;

        Map<String, Boolean> status = new HashMap<>();
        status.put("isOwnProfile", isOwnProfile);
        status.put("isPrivateProfile", isPrivateProfile);
        status.put("isFollowing", isFollowing);
        status.put("hasPendingRequest", hasPendingRequest);
        status.put("canViewProfile", canViewProfile);
        return status;
    }

    @Override
    public long countFollowers(Long userId) {
        return followerRepository.countByFollowingIdAndStatus(userId, FollowStatus.ACCEPTED);
    }

    @Override
    public long countFollowing(Long userId) {
        return followerRepository.countByFollowerIdAndStatus(userId, FollowStatus.ACCEPTED);
    }

    @Override
    public List<ChatContactDTO> getChatContacts(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Follower> following = followerRepository.findFollowingByFollowerId(userId);

        return following.stream()
                .filter(follower -> follower.getStatus() == FollowStatus.ACCEPTED)
                .map(follower -> toChatContactDto(currentUser, follower.getFollowing()))
                .sorted(Comparator.comparing(ChatContactDTO::isMutual).reversed()
                        .thenComparing(contact -> safeSortValue(contact.getUsername())))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllFollowedUsers(Long userId) {
        List<Follower> followers = followerRepository.findFollowingByFollowerId(userId);
        return followers.stream()
                .filter(follower -> follower.getStatus() == FollowStatus.ACCEPTED)
                .map(Follower::getFollowing)
                .collect(Collectors.toList());
    }

    private ChatContactDTO toChatContactDto(User currentUser, User contactUser) {
        boolean isMutual = followerRepository
                .findByFollowerAndFollowingAndStatus(contactUser, currentUser, FollowStatus.ACCEPTED)
                .isPresent();
        Bio bio = contactUser.getBio();

        ChatContactDTO dto = new ChatContactDTO();
        dto.setId(contactUser.getId());
        dto.setUsername(contactUser.getUsername());
        dto.setMutual(isMutual);

        if (bio != null) {
            dto.setFirstName(bio.getFirstName());
            dto.setLastName(bio.getLastName());
            dto.setProfilePictureUrl(bio.getProfilePictureUrl());
        }

        return dto;
    }

    private String safeSortValue(String value) {
        return value == null ? "" : value.toLowerCase();
    }
}

package com.festapp.FestApplication.service;

import com.festapp.FestApplication.models.Follower;
import com.festapp.FestApplication.models.Notification;
import com.festapp.FestApplication.models.Notification.NotificationType;
import com.festapp.FestApplication.models.Follower.FollowStatus;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.FollowerRepository;
import com.festapp.FestApplication.repository.NotificationRepository;
import com.festapp.FestApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

        return followerRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }

    @Override
    @Transactional
    public boolean toggleFollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Follower> existingFollow = followerRepository.findByFollowerAndFollowing(follower, following);

        if (existingFollow.isPresent()) {
            followerRepository.delete(existingFollow.get());
            return false; // Sada više ne prati korisnika
        } else {
            Follower newFollow = new Follower();
            newFollow.setFollower(follower);
            newFollow.setFollowing(following);
            newFollow.setStatus(FollowStatus.ACCEPTED);
            newFollow.setActionDate(LocalDateTime.now());

            followerRepository.save(newFollow);
            
            Notification notification = new Notification(
                    null, // ID će biti generisan automatski
                    following, // Osoba koja dobija notifikaciju (koja je zapraćena)
                    follower.getUsername() + " followed you!", // Sadržaj notifikacije
                    NotificationType.FOLLOW, // Tip notifikacije
                    LocalDateTime.now(),
                    false // Podrazumevano nije pročitana
                );
                notificationRepository.save(notification); // Čuvanje u bazi
            
            
            return true; // Sada prati korisnika
        }
    }

    @Override
    public List<User> getAllFollowedUsers(Long userId) {
        List<Follower> followers = followerRepository.findFollowingByFollowerId(userId);
        return followers.stream()
                       .map(Follower::getFollowing)
                       .collect(Collectors.toList());
    }
    
}


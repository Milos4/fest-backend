package com.festapp.FestApplication.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.ChatContactDTO;
import com.festapp.FestApplication.dto.FollowRequest;
import com.festapp.FestApplication.models.Follower;
import com.festapp.FestApplication.service.FollowerService;

@RestController
@RequestMapping("/api/follow")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @PostMapping("/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestBody FollowRequest request) {
        return ResponseEntity.ok(followerService.isFollowing(request.getFollowerId(), request.getFollowingId()));
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleFollow(@RequestBody FollowRequest request) {
        try {
            boolean isNowFollowing = followerService.toggleFollow(request.getFollowerId(), request.getFollowingId());
            boolean hasPendingRequest = followerService.hasPendingRequest(request.getFollowerId(), request.getFollowingId());
            Map<String, Boolean> response = new HashMap<>();
            response.put("isFollowing", isNowFollowing);
            response.put("hasPendingRequest", hasPendingRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFollowRequest(@RequestBody FollowRequest request) {
        try {
            followerService.acceptFollowRequest(request.getFollowerId(), request.getFollowingId());
            return ResponseEntity.ok(Map.of("accepted", true));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectFollowRequest(@RequestBody FollowRequest request) {
        try {
            followerService.rejectFollowRequest(request.getFollowerId(), request.getFollowingId());
            return ResponseEntity.ok(Map.of("rejected", true));
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Follower>> getPendingFollowRequests(@RequestParam Long userId) {
        return ResponseEntity.ok(followerService.getPendingFollowRequests(userId));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getFollowStatus(@RequestParam Long currentUserId,
            @RequestParam Long profileUserId) {
        return ResponseEntity.ok(followerService.getFollowStatus(currentUserId, profileUserId));
    }

    @GetMapping("/chat-contacts")
    public ResponseEntity<List<ChatContactDTO>> getChatContacts(@RequestParam Long userId) {
        return ResponseEntity.ok(followerService.getChatContacts(userId));
    }
    
}

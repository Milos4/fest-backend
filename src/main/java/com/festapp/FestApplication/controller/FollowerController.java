package com.festapp.FestApplication.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.FollowRequest;
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
    public ResponseEntity<Map<String, Boolean>> toggleFollow(@RequestBody FollowRequest request) {
        boolean isNowFollowing = followerService.toggleFollow(request.getFollowerId(), request.getFollowingId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("isFollowing", isNowFollowing);
        return ResponseEntity.ok(response);
    }
    
}
package com.festapp.FestApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.BioDTO;
import com.festapp.FestApplication.dto.UpdateProfileRequest;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.service.FollowerService;
import com.festapp.FestApplication.service.PostService;
import com.festapp.FestApplication.service.UserService;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
    private final FollowerService followerService;
    private final PostService postService;

	@Autowired
	public UserController(UserService userService, FollowerService followerService, PostService postService) {
		this.userService = userService;
        this.followerService = followerService;
        this.postService = postService;
	}

	@GetMapping
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/search")
	public List<User> searchUsers(@RequestParam String q) {
		return userService.searchUsers(q);
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/{userId}/bio")
    public ResponseEntity<BioDTO> getUserBio(@PathVariable Long userId) {
        Optional<BioDTO> userBio = userService.getUserBioById(userId);
        return userBio.map(bio -> new ResponseEntity<>(bio, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<Map<String, Long>> getUserStats(@PathVariable Long userId) {
        Map<String, Long> stats = Map.of(
                "postCount", postService.countPostsByUser(userId),
                "followersCount", followerService.countFollowers(userId),
                "followingCount", followerService.countFollowing(userId)
        );
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @PutMapping("/{userId}/bio")
    public ResponseEntity<Void> updateUserBio(@PathVariable Long userId, @RequestBody BioDTO bioDTO) {
        userService.updateUserBio(userId, bioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody UpdateProfileRequest request) {
        try {
            User updatedUser = userService.updateProfile(userId, request);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long userId, @RequestBody Map<String, String> passwordRequest) {
        try {
            String oldPassword = passwordRequest.get("oldPassword");
            String newPassword = passwordRequest.get("newPassword");
            String confirmNewPassword = passwordRequest.get("confirmNewPassword");

            userService.changePassword(userId, oldPassword, newPassword, confirmNewPassword);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

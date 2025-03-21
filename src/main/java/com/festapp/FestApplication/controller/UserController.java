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
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
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

    @PutMapping("/{userId}/bio")
    public ResponseEntity<Void> updateUserBio(@PathVariable Long userId, @RequestBody BioDTO bioDTO) {
        userService.updateUserBio(userId, bioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

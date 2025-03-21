package com.festapp.FestApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;

	@Autowired
	public AuthenticationServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User login(String username, String password) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			if (passwordMatches(password, user.getPassword())) {
				return user;
			}
		}
		return null;

	}

	// Method to verify if the provided password matches the hashed password
	private boolean passwordMatches(String rawPassword, String hashedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(rawPassword, hashedPassword);
	}

}

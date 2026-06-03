package com.festapp.FestApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.dto.BioDTO;
import com.festapp.FestApplication.dto.UpdateProfileRequest;
import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	 @Override
	    public User getUserById(Long id) {
	        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	    }

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> searchUsers(String query) {
		return userRepository
				.findByBioFirstNameContainingIgnoreCaseOrBioLastNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
						query, query, query);
	}

	public User findUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<BioDTO> getUserBioById(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.map(user -> {
			Bio bio = user.getBio();
			if (bio != null) {
				return new BioDTO(bio.getProfilePictureUrl(), bio.getLocation(), bio.getInterests(),
						bio.getDateOfBirth(), bio.isPrivateProfile(), bio.getInstagramProfileUrl(),
						bio.getPreferredLanguage(), bio.getFirstName(), bio.getLastName());
			}
			return null;
		});
	}

	@Override
	public void updateUserBio(Long userId, BioDTO bioDTO) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Bio bio = user.getBio();
			if (bio == null) {
				bio = new Bio();
				bio.setUser(user);
				user.setBio(bio);
			}
			bio.setProfilePictureUrl(bioDTO.getProfilePictureUrl());
			bio.setLocation(bioDTO.getLocation());
			bio.setInterests(bioDTO.getInterests());
			bio.setDateOfBirth(bioDTO.getDateOfBirth());
			bio.setPrivateProfile(bioDTO.isPrivateProfile());
			bio.setInstagramProfileUrl(bioDTO.getInstagramProfileUrl());
			bio.setPreferredLanguage(bioDTO.getPreferredLanguage());
			bio.setFirstName(bioDTO.getFirstName());
			bio.setLastName(bioDTO.getLastName());
			userRepository.save(user);
		} else {
		}
	}

	@Override
	public User updateProfile(Long userId, UpdateProfileRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		Bio bio = user.getBio();
		if (bio == null) {
			bio = new Bio();
			bio.setUser(user);
			user.setBio(bio);
		}

		bio.setFirstName(request.getFirstName());
		bio.setLastName(request.getLastName());
		bio.setLocation(request.getLocation());
		bio.setInterests(request.getInterests());
		bio.setInstagramProfileUrl(request.getInstagramProfileUrl());
		bio.setPreferredLanguage(request.getPreferredLanguage());
		bio.setProfilePictureUrl(request.getProfilePictureUrl());
		if (request.getPrivateProfile() != null) {
			bio.setPrivateProfile(request.getPrivateProfile());
		}

		if (request.getDateOfBirth() == null || request.getDateOfBirth().isBlank()) {
			bio.setDateOfBirth(null);
		} else {
			try {
				bio.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
			} catch (DateTimeParseException ex) {
				throw new IllegalArgumentException("Date of birth must be in format yyyy-MM-dd");
			}
		}

		return userRepository.save(user);
	}

	@Override
	public void changePassword(Long userId, String oldPassword, String newPassword, String confirmNewPassword) {
		if (oldPassword == null || oldPassword.isBlank()) {
			throw new IllegalArgumentException("Old password is required");
		}
		if (newPassword == null || newPassword.isBlank()) {
			throw new IllegalArgumentException("New password is required");
		}
		if (!newPassword.equals(confirmNewPassword)) {
			throw new IllegalArgumentException("New passwords do not match");
		}
		if (newPassword.length() < 6) {
			throw new IllegalArgumentException("New password must have at least 6 characters");
		}

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IllegalArgumentException("Old password is not correct");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
}

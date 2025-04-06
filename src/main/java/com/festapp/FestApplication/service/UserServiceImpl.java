package com.festapp.FestApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.dto.BioDTO;
import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	
	 @Override
	    public User getUserById(Long id) {
	        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	    }

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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
}

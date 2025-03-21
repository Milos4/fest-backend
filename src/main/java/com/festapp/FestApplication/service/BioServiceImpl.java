package com.festapp.FestApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.repository.BioRepository;
import com.festapp.FestApplication.repository.UserRepository;

import java.util.Optional;

@Service
public class BioServiceImpl implements BioService {
    private final BioRepository bioRepository;
    private final UserRepository userRepository;

    public BioServiceImpl(BioRepository bioRepository, UserRepository userRepository) {
        this.bioRepository = bioRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Bio updateBio(Long userId, Bio updatedBio) {
    	 return bioRepository.findByUserId(userId)
    	            .map(existingBio -> {
    	                if (updatedBio.getProfilePictureUrl() != null) {
    	                    existingBio.setProfilePictureUrl(updatedBio.getProfilePictureUrl());
    	                }
    	                if (updatedBio.getLocation() != null) {
    	                    existingBio.setLocation(updatedBio.getLocation());
    	                }
    	                if (updatedBio.getInterests() != null) {
    	                    existingBio.setInterests(updatedBio.getInterests());
    	                }
    	                if (updatedBio.getDateOfBirth() != null) {
    	                    existingBio.setDateOfBirth(updatedBio.getDateOfBirth());
    	                }
    	                existingBio.setPrivateProfile(updatedBio.isPrivateProfile()); // boolean ne može biti null
    	                if (updatedBio.getInstagramProfileUrl() != null) {
    	                    existingBio.setInstagramProfileUrl(updatedBio.getInstagramProfileUrl());
    	                }
    	                if (updatedBio.getPreferredLanguage() != null) {
    	                    existingBio.setPreferredLanguage(updatedBio.getPreferredLanguage());
    	                }
    	                if (updatedBio.getFirstName() != null) {
    	                    existingBio.setFirstName(updatedBio.getFirstName());
    	                }
    	                if (updatedBio.getLastName() != null) {
    	                    existingBio.setLastName(updatedBio.getLastName());
    	                }
    	                return bioRepository.save(existingBio);
    	            })
    	            .orElseThrow(() -> new RuntimeException("Bio not found for userId: " + userId));
    	}

    @Override
    public Optional<Bio> getBioByUserId(Long userId) {
        return bioRepository.findByUserId(userId);
    }
}

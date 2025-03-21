package com.festapp.FestApplication.service;

import java.util.Optional;

import com.festapp.FestApplication.models.Bio;

public interface BioService {
	
	Bio updateBio(Long userId, Bio updatedBio);
    Optional<Bio> getBioByUserId(Long userId);

}

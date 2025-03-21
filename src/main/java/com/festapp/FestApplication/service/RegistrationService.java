package com.festapp.FestApplication.service;

import com.festapp.FestApplication.dto.RegistrationResponse;

public interface RegistrationService {

	RegistrationResponse register(String username, String email, String password, String confirmPassword);
}

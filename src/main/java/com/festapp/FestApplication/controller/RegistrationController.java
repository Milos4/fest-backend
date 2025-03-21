package com.festapp.FestApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.festapp.FestApplication.dto.RegistrationRequest;
import com.festapp.FestApplication.dto.RegistrationResponse;
import com.festapp.FestApplication.service.RegistrationService;

@RestController
public class RegistrationController {

	private final RegistrationService registrationService;

	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping("/api/register")
	public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
		String username = registrationRequest.getRegUsername();
		String email = registrationRequest.getRegEmail();
		String password = registrationRequest.getRegPassword();
		String confirmPassword = registrationRequest.getRegConfirmPassword();

		// Call the registration service
		RegistrationResponse registrationResponse = registrationService.register(username, email, password, confirmPassword);

		if (registrationResponse.isSuccess()) {
            return ResponseEntity.ok(registrationResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registrationResponse);
        }
	}
}

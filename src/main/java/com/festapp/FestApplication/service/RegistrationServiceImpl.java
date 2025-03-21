package com.festapp.FestApplication.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.festapp.FestApplication.dto.RegistrationResponse;
import com.festapp.FestApplication.models.User;
import com.festapp.FestApplication.models.Role;
import com.festapp.FestApplication.models.Bio;
import com.festapp.FestApplication.models.Role.UserRole;
import com.festapp.FestApplication.repository.BioRepository;
import com.festapp.FestApplication.repository.RoleRepository;
import com.festapp.FestApplication.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class RegistrationServiceImpl implements RegistrationService {
	
    private final BCryptPasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final BioRepository bioRepository;
 
	@Autowired
	public RegistrationServiceImpl(UserRepository userRepository,RoleRepository roleRepository,BioRepository bioRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bioRepository = bioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public RegistrationResponse register(String username, String email, String password, String confirmPassword) {
		RegistrationResponse response = new RegistrationResponse();
		response.setSuccess(false);

		// Check if username is already taken
		if (userRepository.existsByUsername(username)) {
			response.setMessage("Username already exists");
			return response;
		}

		// Check if email is already registered
		if (userRepository.existsByEmail(email)) {
			response.setMessage("Email already exists");
			return response;
		}

		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			response.setMessage("Passwords don't match");
			return response;
		}
		
		// Create a new role for the user
	    Role role = roleRepository.findByName(UserRole.ADMIN);
	
	    String hashedPassword = passwordEncoder.encode(password);
	    
		// Create a new user
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setPassword(hashedPassword);
		newUser.setRegistrationDate(new Date());// Set registration date
		newUser.setRole(role); // Assign the role to the user
	    
		// Save the new user
		User savedUser = userRepository.save(newUser);
		
		// Create a new bio for the user
		Bio newBio = new Bio();
		// Set other fields of the bio as needed
		newBio.setUser(savedUser); // Set the user association

		// Save the new bio
		bioRepository.save(newBio);
		

		response.setSuccess(true);
		response.setUser(savedUser);
		response.setMessage("Registration successful");

		return response;
	}

}

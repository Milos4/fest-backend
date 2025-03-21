package com.festapp.FestApplication.service;

import com.festapp.FestApplication.models.User;

public interface AuthenticationService {
	
    User login(String username, String password);

}

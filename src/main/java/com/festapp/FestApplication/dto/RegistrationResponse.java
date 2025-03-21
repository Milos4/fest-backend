package com.festapp.FestApplication.dto;

import com.festapp.FestApplication.models.User;

public class RegistrationResponse {
    private boolean success;
    private String message;
    private User user;
    
    public RegistrationResponse() {
    	
    }
    
	public RegistrationResponse(boolean success, String message, User user) {
		super();
		this.success = success;
		this.message = message;
		this.user = user;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

    
}
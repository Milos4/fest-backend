package com.festapp.FestApplication.dto;

public class RegistrationRequest {
    private String regUsername;
    private String regPassword;
    private String regConfirmPassword;
    private String regEmail;

    // Constructors, getters, and setters

    public RegistrationRequest() {
    }

    public RegistrationRequest(String regUsername, String regEmail, String regPassword, String regConfirmPassword) {
        this.regUsername = regUsername;
        this.regEmail = regEmail;

        this.regPassword = regPassword;
        this.regConfirmPassword = regConfirmPassword;
    }

	public String getRegUsername() {
		return regUsername;
	}

	public void setRegUsername(String regUsername) {
		this.regUsername = regUsername;
	}

	public String getRegPassword() {
		return regPassword;
	}

	public void setRegPassword(String regPassword) {
		this.regPassword = regPassword;
	}

	public String getRegConfirmPassword() {
		return regConfirmPassword;
	}

	public void setRegConfirmPassword(String regConfirmPassword) {
		this.regConfirmPassword = regConfirmPassword;
	}

	public String getRegEmail() {
		return regEmail;
	}

	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
	}
    
}

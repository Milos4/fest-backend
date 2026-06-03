package com.festapp.FestApplication.dto;

import java.util.List;

public class UpdateProfileRequest {
	private String firstName;
	private String lastName;
	private String location;
	private List<String> interests;
	private String dateOfBirth;
	private String instagramProfileUrl;
	private String preferredLanguage;
	private String profilePictureUrl;
	private Boolean privateProfile;

	public UpdateProfileRequest() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getInstagramProfileUrl() {
		return instagramProfileUrl;
	}

	public void setInstagramProfileUrl(String instagramProfileUrl) {
		this.instagramProfileUrl = instagramProfileUrl;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public Boolean getPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(Boolean privateProfile) {
		this.privateProfile = privateProfile;
	}
}

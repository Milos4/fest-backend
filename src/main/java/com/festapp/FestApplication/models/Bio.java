package com.festapp.FestApplication.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "bio")
public class Bio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String profilePictureUrl;
	private String location;
    @ElementCollection
    @CollectionTable(name = "bio_interests", joinColumns = @JoinColumn(name = "bio_id"))
    @Column(name = "interest")
    private List<String> interests; 
	private LocalDate dateOfBirth;
	private boolean privateProfile;
	private String instagramProfileUrl;
	private String preferredLanguage;
	private String firstName;
	private String lastName;
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	public Bio() {

	}

	public Bio(Long id, String profilePictureUrl, String location, List<String>interests, LocalDate dateOfBirth,
			boolean privateProfile, String instagramProfileUrl, String preferredLanguage, String firstName,
			String lastName, User user) {
		super();
		this.id = id;
		this.profilePictureUrl = profilePictureUrl;
		this.location = location;
		this.interests = interests;
		this.dateOfBirth = dateOfBirth;
		this.privateProfile = privateProfile;
		this.instagramProfileUrl = instagramProfileUrl;
		this.preferredLanguage = preferredLanguage;
		this.firstName = firstName;
		this.lastName = lastName;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(boolean privateProfile) {
		this.privateProfile = privateProfile;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
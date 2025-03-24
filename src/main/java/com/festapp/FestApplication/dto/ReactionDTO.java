package com.festapp.FestApplication.dto;

public class ReactionDTO {
	private Long id;
	private String type;
	private String username;
	private Long userID;

	// Konstruktor
	public ReactionDTO(Long id, String type, String username, Long userID) {
		this.id = id;
		this.type = type;
		this.username = username;
		this.userID = userID;
	}

	public ReactionDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getteri i setteri
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}
}
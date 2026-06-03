package com.festapp.FestApplication.dto;

import com.festapp.FestApplication.models.Comment;

public class CommentDTO {
	private Long id;
	private String content;
	private String username;
	private Long userID;
	private String userProfilePictureUrl;

	public CommentDTO() {

	}

	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.content = comment.getContent();
		this.username = comment.getUser().getUsername();
		this.userID = comment.getUser().getId();
		if (comment.getUser().getBio() != null) {
			this.userProfilePictureUrl = comment.getUser().getBio().getProfilePictureUrl();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getUserProfilePictureUrl() {
		return userProfilePictureUrl;
	}

	public void setUserProfilePictureUrl(String userProfilePictureUrl) {
		this.userProfilePictureUrl = userProfilePictureUrl;
	}

}

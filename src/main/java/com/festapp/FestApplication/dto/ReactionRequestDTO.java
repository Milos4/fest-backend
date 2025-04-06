package com.festapp.FestApplication.dto;

import com.festapp.FestApplication.models.Reaction.ReactionType;

public class ReactionRequestDTO {
	private String type;
	private Long userId;
	private Long postId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}
}
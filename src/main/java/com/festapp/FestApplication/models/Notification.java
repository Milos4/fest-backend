package com.festapp.FestApplication.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt;
    
    private boolean isRead = false; 

    public Notification() {
    }

    public Notification(Long id, User user, String content, NotificationType type, LocalDateTime createdAt, boolean isRead) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

	public enum NotificationType {
        FOLLOW_REQUEST,
        FOLLOW_ACCEPTED,
        ACCEPTED_ON_EVENT,
        FOLLOW,
        // Add more types as needed
    }
}

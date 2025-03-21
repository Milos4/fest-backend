package com.festapp.FestApplication.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    public Follower() {
    }

    public Follower(Long id, User follower, User following, FollowStatus status, LocalDateTime actionDate) {
        this.id = id;
        this.follower = follower;
        this.following = following;
        this.status = status;
        this.actionDate = actionDate;
    }

    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowing() {
		return following;
	}

	public void setFollowing(User following) {
		this.following = following;
	}

	public FollowStatus getStatus() {
		return status;
	}

	public void setStatus(FollowStatus status) {
		this.status = status;
	}

	public LocalDateTime getActionDate() {
		return actionDate;
	}

	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}



	public enum FollowStatus {
        PENDING,
        ACCEPTED,
        // Add more status as needed
    }
}

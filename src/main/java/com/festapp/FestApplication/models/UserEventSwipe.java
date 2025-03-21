package com.festapp.FestApplication.models;

import jakarta.persistence.*;

@Entity
public class UserEventSwipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_event_id")
    private UserEvent userEvent;

    private String message;
    private int numberOfMen;
    private int numberOfWomen;
    private SwipeStatus status;

    public UserEventSwipe() {
    }

    public UserEventSwipe(Long id, User user, UserEvent userEvent, String message,
                          int numberOfMen, int numberOfWomen, SwipeStatus status) {
        this.id = id;
        this.user = user;
        this.userEvent = userEvent;
        this.message = message;
        this.numberOfMen = numberOfMen;
        this.numberOfWomen = numberOfWomen;
        this.status = status;
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

	public UserEvent getUserEvent() {
		return userEvent;
	}

	public void setUserEvent(UserEvent userEvent) {
		this.userEvent = userEvent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNumberOfMen() {
		return numberOfMen;
	}

	public void setNumberOfMen(int numberOfMen) {
		this.numberOfMen = numberOfMen;
	}

	public int getNumberOfWomen() {
		return numberOfWomen;
	}

	public void setNumberOfWomen(int numberOfWomen) {
		this.numberOfWomen = numberOfWomen;
	}

	public SwipeStatus getStatus() {
		return status;
	}

	public void setStatus(SwipeStatus status) {
		this.status = status;
	}

	public enum SwipeStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }
}

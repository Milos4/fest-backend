package com.festapp.FestApplication.models;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rater_user_id")
    private User raterUser;

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    private int rating;

    public Rating() {
    }

    public Rating(Long id, User raterUser, User ratedUser, int rating) {
        this.id = id;
        this.raterUser = raterUser;
        this.ratedUser = ratedUser;
        this.rating = rating;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRaterUser() {
		return raterUser;
	}

	public void setRaterUser(User raterUser) {
		this.raterUser = raterUser;
	}

	public User getRatedUser() {
		return ratedUser;
	}

	public void setRatedUser(User ratedUser) {
		this.ratedUser = ratedUser;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

    
}

package com.festapp.FestApplication.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "user")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String email;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Bio bio;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;


	public User() {
		this.registrationDate = new Date();
	}
	
	

	public User(String username, String password, String email, Bio bio, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.bio = bio;
		this.role = role;
		this.registrationDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Bio getBio() {
		return bio;
	}

	public void setBio(Bio bio) {
		this.bio = bio;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}
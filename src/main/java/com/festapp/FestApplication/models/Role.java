package com.festapp.FestApplication.models;

import jakarta.persistence.*;

@Entity
@Table(name = "role")

public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private UserRole name;

	public Role() {

	}

	public Role( UserRole name) {
	
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRole getName() {
		return name;
	}

	public void setName(UserRole name) {
		this.name = name;
	}

	public enum UserRole {
		USER, ADMIN
	}
}
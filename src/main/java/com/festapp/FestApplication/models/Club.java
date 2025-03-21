package com.festapp.FestApplication.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location; 
    private Double latitude;
    private Double longitude;
    private TypeClub type;

    @OneToOne
    @JoinColumn(name = "admin_user_id")
    private User admin;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubEvent> events;

    
    public Club(){
  
    }
    
    public Club(Long id, String name, String location, Double latitude, Double longitude, TypeClub type,
			User admin) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.admin = admin;
	}
    
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public TypeClub getType() {
		return type;
	}

	public void setType(TypeClub type) {
		this.type = type;
	}
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public List<ClubEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ClubEvent> events) {
		this.events = events;
	}

	public enum TypeClub {
        CLUB,
        CAFFE,
        PUB,
        DISCO,
    }
}
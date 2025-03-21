package com.festapp.FestApplication.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class UserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Double latitude;
    private Double longitude; 
    private int maxAttendees;
    private String type;
    private int attendeesCountM;
    private int attendeesCountW;


    @ManyToOne
    @JoinColumn(name = "host_user_id")
    private User hostUser;

    @OneToMany(mappedBy = "userEvent", cascade = CascadeType.ALL)
    private List<UserEventSwipe> swipes;

    public UserEvent() {
    }

    public UserEvent(Long id, String name, String description, LocalDateTime startTime, LocalDateTime endTime,
                     String location, Double latitude, Double longitude, int maxAttendees, String type, int attendeesCountM, int attendeesCountW, User hostUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxAttendees = maxAttendees;
        this.type = type;
        this.attendeesCountM = attendeesCountM;
        this.attendeesCountW = attendeesCountW;
        this.hostUser = hostUser;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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

	public int getMaxAttendees() {
		return maxAttendees;
	}

	public void setMaxAttendees(int maxAttendees) {
		this.maxAttendees = maxAttendees;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	public int getAttendeesCountM() {
		return attendeesCountM;
	}

	public void setAttendeesCountM(int attendeesCountM) {
		this.attendeesCountM = attendeesCountM;
	}

	public int getAttendeesCountW() {
		return attendeesCountW;
	}

	public void setAttendeesCountW(int attendeesCountW) {
		this.attendeesCountW = attendeesCountW;
	}

	public User getHostUser() {
		return hostUser;
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}

	public List<UserEventSwipe> getSwipes() {
		return swipes;
	}

	public void setSwipes(List<UserEventSwipe> swipes) {
		this.swipes = swipes;
	}
    
    

}

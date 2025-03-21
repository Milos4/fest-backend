package com.festapp.FestApplication.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class ClubEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date date;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TypeEvent typeEvent;
    
    
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public ClubEvent() {
    }

    public ClubEvent(Long id, String name, Date date, String description, TypeEvent typeEvent, Club club) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.typeEvent = typeEvent;
        this.club = club;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypeEvent getTypeEvent() {
		return typeEvent;
	}

	public void setTypeEvent(TypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public enum TypeEvent {
        ROCK,
        METAL,
        POP,
        QUIZ,
        OTHER,
        // Add more event types as needed
    }
}
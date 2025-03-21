package com.festapp.FestApplication.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class MonthlyPaymentClub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_request_id")
    private ClubRequest clubRequest;

    private double amount;
    private Date paymentDate;
    
    public MonthlyPaymentClub() {
    	
    }
    
	public MonthlyPaymentClub(Long id, ClubRequest clubRequest, double amount, Date paymentDate) {
		super();
		this.id = id;
		this.clubRequest = clubRequest;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClubRequest getClubRequest() {
		return clubRequest;
	}
	public void setClubRequest(ClubRequest clubRequest) {
		this.clubRequest = clubRequest;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

    
	
}
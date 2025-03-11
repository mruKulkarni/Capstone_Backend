package com.example.Appointment.Review;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="review")
public class Review{
	@Id
	@GeneratedValue
	private Integer id;
	private Integer rating;
	private String comments;
	
	@OneToOne
    	@JoinColumn(name = "doctorId", referencedColumnName = "id", unique = true)
    	private Doctor doctor;

	@OneToOne
    	@JoinColumn(name = "userId", referencedColumnName = "id", unique = true)
    	private User user;

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Review(Integer id, Integer rating, String comments, Doctor doctor, User user) {
		super();
		this.id = id;
		this.rating = rating;
		this.comments = comments;
		this.doctor = doctor;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
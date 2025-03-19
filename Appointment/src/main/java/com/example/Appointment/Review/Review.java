package com.example.Appointment.Review;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer rating;
	@Column(columnDefinition = "TEXT")
	private String comments;

	@ManyToOne
	@JoinColumn(name = "doctorId", referencedColumnName = "id")
	@JsonBackReference
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonBackReference
	private User user;

	@OneToOne
	@JoinColumn(name = "appointment_id", nullable = false, unique = true)
	private Appointment appointment;

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Review() {
		super();
	}

	public Review(Integer id, Integer rating, String comments, Doctor doctor, User user) {
		super();
		this.id = id;
		this.rating = rating;
		this.comments = comments;
		this.doctor = doctor;
		this.user = user;
	}

	public Review(Integer rating, String comments, Doctor doctor, User user) {
		super();
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
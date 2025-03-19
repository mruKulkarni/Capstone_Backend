package com.example.Appointment.Appointment;

import java.sql.Date;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue
	private Integer id;
	private String slot;
	private String status;
	private Date date;

	@ManyToOne // A doctor can have multiple appointments
	@JoinColumn(name = "doctorId", referencedColumnName = "id")
	@JsonBackReference
	private Doctor doctor;

	@ManyToOne // A user can have multiple appointments
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonBackReference
	@JsonIgnore

	private User user;

	public Appointment() {
		super();
	}

	public Appointment(Integer id, String slot, String status, Date date, Doctor doctor, User user) {
		super();
		this.id = id;
		this.slot = slot;
		this.status = status;
		this.date = date;
		this.doctor = doctor;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return (date != null) ? date.toString() : null; // ✅ Convert SQL Date to String
	}

	public void setDate(Date date) {
		this.date = date;
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
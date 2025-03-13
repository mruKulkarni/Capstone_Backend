package com.example.Appointment.Appointment;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.User.User;
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
	private Integer slot;
	private String status;

	@ManyToOne // A doctor can have multiple appointments
	@JoinColumn(name = "doctorId", referencedColumnName = "id")
	private Doctor doctor;

	@ManyToOne // A user can have multiple appointments
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonIgnore

	private User user;

	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Appointment(Integer id, Integer slot, String status, Doctor doctor, User user) {
		super();
		this.id = id;
		this.slot = slot;
		this.status = status;
		this.doctor = doctor;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
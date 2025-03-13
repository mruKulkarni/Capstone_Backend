package com.example.Appointment.User;

import java.util.List;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Review.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Integer age;
	@Column(unique = true,nullable = false)
	private String email;
	private String phone;
	private String password;
	private String gender;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Review> reviews;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Appointment> appointments;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer id, String name, Integer age, String email, String phone, String password, String gender,
			List<Review> reviews, List<Appointment> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.gender = gender;
		this.reviews = reviews;
		this.appointments = appointments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

}

package com.example.Appointment.Doctor;

import java.util.List;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Review.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(unique = true, nullable = false)
	private String doctorCode;
	private String name;
	private String qualification;
	private Double averageRating;

	@OneToMany(mappedBy = "doctor")
	@JsonManagedReference
	private List<Review> reviews;

	@ManyToOne
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	@JsonBackReference
	private Department department;

	@Column(name = "is_active")
	private boolean isActive = true;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Doctor(Integer id, String doctorCode, String name, String qualification, Double averageRating,
			List<Review> reviews, Department department) {
		super();
		this.id = id;
		this.doctorCode = doctorCode;
		this.name = name;
		this.qualification = qualification;
		this.averageRating = averageRating;
		this.reviews = reviews;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public void setAverageRating() {
		this.averageRating = 5.0;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}

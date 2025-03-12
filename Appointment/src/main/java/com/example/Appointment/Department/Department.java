package com.example.Appointment.Department;

import java.util.List;

import com.example.Appointment.Doctor.Doctor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	@Id
	@GeneratedValue
	private Integer Id;
	private String Name;

	@OneToMany(mappedBy = "department")
	private List<Doctor> doctors;

	public Department() {
		super();
	
	}

	public Department(Integer id, String name, List<Doctor> doctors) {
		super();
		Id = id;
		Name = name;
		this.doctors = doctors;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	
}

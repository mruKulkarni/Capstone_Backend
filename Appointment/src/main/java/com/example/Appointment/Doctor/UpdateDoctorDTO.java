package com.example.Appointment.Doctor;

public class UpdateDoctorDTO {
	private String name;
	private String qualification;

	// Getters and Setters
	public String getName() {
		return name;
	}

	public UpdateDoctorDTO(String name, String qualification) {
		super();
		this.name = name;
		this.qualification = qualification;
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
}

package com.example.Appointment.Doctor;

public class DoctorDTO {
	private String doctorCode;
	private String name;
	private String qualification;
	private Double averageRating;
	private Integer departmentId;

	public DoctorDTO(String doctorCode, String name, String qualification, Double averageRating, Integer departmentId) {
		super();
		this.doctorCode = doctorCode;
		this.name = name;
		this.qualification = qualification;
		this.averageRating = averageRating;
		this.departmentId = departmentId;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

}

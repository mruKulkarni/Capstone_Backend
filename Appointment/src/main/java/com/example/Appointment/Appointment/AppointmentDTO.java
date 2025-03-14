package com.example.Appointment.Appointment;

import java.sql.Date;

public class AppointmentDTO {
	private String userName;
	private String doctorName;
	private String department;
	private Date date; // Keep as Date since it's coming from DB
	private String slot;
	private String status;

	public AppointmentDTO(String userName, String doctorName, String department, Date date, String slot,
			String status) {
		this.userName = userName;
		this.doctorName = doctorName;
		this.department = department;
		this.date = date;
		this.slot = slot;
		this.status = status;
	}

	// ✅ Getters & Setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDate() {
		return date;
	} // Keep Date type

	public void setDate(Date date) {
		this.date = date;
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
}

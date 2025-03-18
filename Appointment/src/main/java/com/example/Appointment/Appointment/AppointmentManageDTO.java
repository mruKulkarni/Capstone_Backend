package com.example.Appointment.Appointment;

public class AppointmentManageDTO {
	private Integer appointmentId;
	private Integer doctorId;
	private String userName;
	private String doctorName;
	private String department;
	private String date; // Keep as Date since it's coming from DB
	private String slot;
	private String status;
	public AppointmentManageDTO() {
		super();
	}
	public AppointmentManageDTO(Integer doctorId, String userName, String doctorName, String department, String date,
			String slot, String status,Integer appointmentId ) {
		super();
		this.doctorId = doctorId;
		this.userName = userName;
		this.doctorName = doctorName;
		this.department = department;
		this.date = date;
		this.slot = slot;
		this.status = status;
		this.appointmentId= appointmentId;
	}
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
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

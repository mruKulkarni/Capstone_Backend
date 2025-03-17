package com.example.Appointment.Review;

public class ReviewDTO {
	private Integer userId;
	private Integer doctorId;
	private Integer appointmentId;
	private String doctorName;

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public ReviewDTO(Integer userId, Integer doctorId, Integer appointmentId, String doctorName, Integer rating,
			String comments) {
		super();
		this.userId = userId;
		this.doctorId = doctorId;
		this.appointmentId = appointmentId;
		this.doctorName = doctorName;
		this.rating = rating;
		this.comments = comments;
	}

	private Integer rating;
	private String comments;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
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

}

package com.example.Appointment.Appointment;

import java.sql.Date;

public class AppointmentRequestDTO {
    private int doctorId;
    private int userId;
    private String slot;
    private String date;
    private String email;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = (date !=null) ? Date.valueOf(date).toString(): null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	
}

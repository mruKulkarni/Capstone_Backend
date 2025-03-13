package com.example.Appointment.Review;

import com.example.Appointment.Appointment.Appointment;

public interface ReviewService {
	Appointment getLatestAppointment(Integer userId);

	Review submitReview(Integer userId, Integer doctorId, Integer rating, String comments);
}

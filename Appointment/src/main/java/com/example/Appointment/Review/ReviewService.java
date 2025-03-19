package com.example.Appointment.Review;

import java.util.List;

import com.example.Appointment.Appointment.Appointment;

public interface ReviewService {
	Appointment getLatestAppointment(Integer userId);

	Review submitReview(Integer userId, Integer doctorId, Integer rating, String comments);

	void submitReview(ReviewDTO reviewDTO);

	List<ReviewDTO> getReviewsByUser(Integer userId);

	List<ReviewDTO> getReviewsByDoctor(Integer doctorId);
	
	public List<DoctorReviewDTO> getReviewByDoctor(Integer doctorId);
}

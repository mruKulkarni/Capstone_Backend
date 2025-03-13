package com.example.Appointment.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Appointment.AppointmentRepository;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.User.User;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	public Appointment getLatestAppointment(Integer userId) {
		return appointmentRepository.findTopByUserIdOrderByIdDesc(userId);
	}

	public Review submitReview(Integer userId, Integer doctorId, Integer rating, String comments) {
		Doctor doctor = new Doctor();
		doctor.setId(doctorId);

		User user = new User();
		user.setId(userId);

		Review review = new Review(rating, comments, doctor, user);
		return reviewRepository.save(review);
	}
}

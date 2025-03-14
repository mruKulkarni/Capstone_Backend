package com.example.Appointment.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Appointment.Appointment.AppointmentDTO;
import com.example.Appointment.Appointment.AppointmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/latest-appointment/{userId}")
	public ResponseEntity<AppointmentDTO> getLatestAppointment(@PathVariable Integer userId) {
		AppointmentDTO latestAppointment = appointmentService.getLatestAppointmentByUserDTO(userId);
		return ResponseEntity.ok(latestAppointment);
	}

	@PostMapping("/submit")
	public ResponseEntity<Review> submitReview(@RequestBody ReviewDTO request) {
		Review review = reviewService.submitReview(request.getUserId(), request.getDoctorId(), request.getRating(),
				request.getComments());
		return ResponseEntity.ok(review);
	}
}

package com.example.Appointment.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//	@PostMapping("/submit")
//	public ResponseEntity<Review> submitReview(@RequestBody ReviewDTO request) {
//		Review review = reviewService.submitReview(request.getUserId(), request.getDoctorId(), request.getRating(),
//				request.getComments());
//		return ResponseEntity.ok(review);
//	}

	@PostMapping("/submit")
	public ResponseEntity<Map<String, String>> submitReview(@RequestBody ReviewDTO reviewDTO) {
		reviewService.submitReview(reviewDTO);

		// ✅ Return a JSON response instead of plain text
		Map<String, String> response = new HashMap<>();
		response.put("message", "Review submitted successfully");

		return ResponseEntity.ok(response); // ✅ This is now valid JSON
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ReviewDTO>> getUserReviews(@PathVariable Integer userId) {
		return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
	}

	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<DoctorReviewDTO>> getDoctorReviews(@PathVariable Integer doctorId) {
	    return ResponseEntity.ok(reviewService.getReviewByDoctor(doctorId));
	}
}

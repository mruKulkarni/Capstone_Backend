package com.example.Appointment.Review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Appointment.AppointmentRepository;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorRepository;
import com.example.Appointment.User.User;
import com.example.Appointment.User.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Appointment getLatestAppointment(Integer userId) {
		return appointmentRepository.findTopByUserIdOrderByIdDesc(userId);
	}

	@Override
	public Review submitReview(Integer userId, Integer doctorId, Integer rating, String comments) {
		Doctor doctor = new Doctor();
		doctor.setId(doctorId);

		User user = new User();
		user.setId(userId);

		Review review = new Review(rating, comments, doctor, user);
		return reviewRepository.save(review);
	}

	@Override
	public void submitReview(ReviewDTO reviewDTO) {
		// ✅ Validate appointment
		Appointment appointment = appointmentRepository.findById(reviewDTO.getAppointmentId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

		// ✅ Ensure appointment is marked as "Completed"
		if (!"Completed".equals(appointment.getStatus())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot review an incomplete appointment");
		}

		// ✅ Ensure review does not already exist for this appointment
		if (reviewRepository.existsByAppointmentId(reviewDTO.getAppointmentId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review already submitted for this appointment");
		}

		// ✅ Fetch doctor and user
		Doctor doctor = doctorRepository.findById(reviewDTO.getDoctorId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

		User user = userRepository.findById(reviewDTO.getUserId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

		// ✅ Save review
		Review review = new Review();
		review.setDoctor(doctor);
		review.setUser(user);
		review.setAppointment(appointment);
		review.setRating(reviewDTO.getRating());
		review.setComments(reviewDTO.getComments());

		reviewRepository.save(review);

		updateDoctorRating(reviewDTO.getDoctorId());
	}

	private void updateDoctorRating(Integer doctorId) {
		List<Review> reviews = reviewRepository.findByDoctorId(doctorId);
		if (!reviews.isEmpty()) {
			double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(5.0);
			Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
			doctor.setAverageRating(averageRating);
			doctorRepository.save(doctor); // Save updated rating
		}
	}

	@Override
	public List<ReviewDTO> getReviewsByUser(Integer userId) {
		List<Review> reviews = reviewRepository.findByUserId(userId);
		return reviews.stream()
				.map(review -> new ReviewDTO(review.getDoctor().getId(), review.getUser().getId(),
						review.getAppointment().getId(), review.getDoctor().getName(), review.getRating(),
						review.getComments()))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReviewDTO> getReviewsByDoctor(Integer doctorId) {
		List<Review> reviews = reviewRepository.findByDoctorId(doctorId);
		return reviews.stream()
				.map(review -> new ReviewDTO(review.getDoctor().getId(), review.getUser().getId(),
						review.getAppointment().getId(), review.getDoctor().getName(), review.getRating(),
						review.getComments()))
				.collect(Collectors.toList());

	}
	
	@Override
	public List<DoctorReviewDTO> getReviewByDoctor(Integer doctorId) {
	    List<Review> reviews = reviewRepository.findByDoctorId(doctorId);
	    return reviews.stream()
	            .map(review -> new DoctorReviewDTO(
	                review.getRating(),
	                review.getComments(),
	                review.getUser().getName() // ✅ Fetch reviewer’s name
	            ))
	            .collect(Collectors.toList());
	}
}

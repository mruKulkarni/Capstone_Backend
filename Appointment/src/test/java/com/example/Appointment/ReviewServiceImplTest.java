package com.example.Appointment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Appointment.AppointmentRepository;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorRepository;
import com.example.Appointment.Review.DoctorReviewDTO;
import com.example.Appointment.Review.Review;
import com.example.Appointment.Review.ReviewDTO;
import com.example.Appointment.Review.ReviewRepository;
import com.example.Appointment.Review.ReviewServiceImpl;
import com.example.Appointment.User.User;
import com.example.Appointment.User.UserRepository;

@ExtendWith(MockitoExtension.class)

public class ReviewServiceImplTest {
	@Mock
	private AppointmentRepository appointmentRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private DoctorRepository doctorRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ReviewServiceImpl reviewService;

	private Appointment appointment;
	private Doctor doctor;
	private User user;
	private Review savedReview;

//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//
//		appointment = new Appointment();
//		appointment.setId(3);
//		appointment.setStatus("Completed");
//
//		doctor = new Doctor();
//		doctor.setId(2);
//		doctor.setName("Dr. Smith");
//
//		user = new User();
//		user.setId(1);
//		user.setName("John Doe");
//
//		savedReview = new Review(5, "Great doctor!", doctor, user);
//	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		appointment = new Appointment();
		appointment.setId(3);
		appointment.setStatus("Completed");

		doctor = new Doctor();
		doctor.setId(2);
		doctor.setName("Dr. Smith");

		user = new User();
		user.setId(1);
		user.setName("John Doe");

		// Ensure review has an appointment
		savedReview = new Review(5, "Great doctor!", doctor, user);
		savedReview.setAppointment(appointment);
	}

	@Test
	void testSubmitReview_Success() {
		ReviewDTO reviewDTO = new ReviewDTO(2, 1, 3, "Dr. Smith", 5, "Great doctor!");

		when(appointmentRepository.findById(3)).thenReturn(Optional.of(appointment));
		when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor)); // Fixed doctor ID
		when(userRepository.findById(2)).thenReturn(Optional.of(user));
		when(reviewRepository.existsByAppointmentId(3)).thenReturn(false);
		when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

		assertDoesNotThrow(() -> reviewService.submitReview(reviewDTO));
	}

	@Test
	void testSubmitReview_Fails_AppointmentNotFound() {
		ReviewDTO reviewDTO = new ReviewDTO(2, 1, 3, "Dr. Smith", 5, "Great doctor!");

		when(appointmentRepository.findById(3)).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> reviewService.submitReview(reviewDTO));

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		assertTrue(exception.getReason().contains("Appointment not found"));
	}

	@Test
	void testSubmitReview_Fails_AppointmentNotCompleted() {
		appointment.setStatus("Scheduled");

		ReviewDTO reviewDTO = new ReviewDTO(2, 1, 3, "Dr. Smith", 5, "Great doctor!");

		when(appointmentRepository.findById(3)).thenReturn(Optional.of(appointment));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> reviewService.submitReview(reviewDTO));

		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		assertTrue(exception.getReason().contains("Cannot review an incomplete appointment"));
	}

	@Test
	void testSubmitReview_Fails_ReviewAlreadyExists() {
		ReviewDTO reviewDTO = new ReviewDTO(2, 1, 3, "Dr. Smith", 5, "Great doctor!");

		when(appointmentRepository.findById(3)).thenReturn(Optional.of(appointment));
		when(reviewRepository.existsByAppointmentId(3)).thenReturn(true);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> reviewService.submitReview(reviewDTO));

		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		assertTrue(exception.getReason().contains("Review already submitted for this appointment"));
	}

	@Test
	void testGetReviewsByUser_Success() {
		Review review1 = new Review(5, "Great doctor!", doctor, user);
		review1.setAppointment(appointment); // Ensure it has an appointment

		Review review2 = new Review(4, "Good experience", doctor, user);
		review2.setAppointment(appointment); // Ensure it has an appointment

		when(reviewRepository.findByUserId(1)).thenReturn(Arrays.asList(review1, review2));

		List<ReviewDTO> reviews = reviewService.getReviewsByUser(1);

		assertEquals(2, reviews.size());
		assertEquals("Great doctor!", reviews.get(0).getComments());
		assertEquals(5, reviews.get(0).getRating());
		assertEquals("Good experience", reviews.get(1).getComments());
	}

	@Test
	void testGetReviewsByDoctor_Success() {
		Review review1 = new Review(5, "Excellent!", doctor, user);
		review1.setAppointment(appointment); // Ensure it has an appointment

		Review review2 = new Review(3, "Okay", doctor, user);
		review2.setAppointment(appointment); // Ensure it has an appointment

		when(reviewRepository.findByDoctorId(2)).thenReturn(Arrays.asList(review1, review2));

		List<ReviewDTO> reviews = reviewService.getReviewsByDoctor(2);

		assertEquals(2, reviews.size());
		assertEquals("Excellent!", reviews.get(0).getComments());
		assertEquals(5, reviews.get(0).getRating());
	}

	@Test
	void testGetReviewByDoctor_Success() {
		Review review1 = new Review(5, "Very good!", doctor, user);
		Review review2 = new Review(2, "Not satisfied", doctor, user);

		when(reviewRepository.findByDoctorId(2)).thenReturn(Arrays.asList(review1, review2));

		List<DoctorReviewDTO> reviews = reviewService.getReviewByDoctor(2);

		assertEquals(2, reviews.size());
		assertEquals("Very good!", reviews.get(0).getComments());
//		assertEquals("John Doe", reviews.get(0)..get());
		assertEquals(5, reviews.get(0).getRating());
	}
}

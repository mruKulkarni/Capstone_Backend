package com.example.Appointment.Appointment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorRepository;
import com.example.Appointment.Review.Review;
import com.example.Appointment.Review.ReviewRepository;
import com.example.Appointment.User.User;
import com.example.Appointment.User.UserRepository;
import com.example.Appointment.User.UserService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public Map<String, Object> getLatestAppointmentByUser(Integer userId) {
		Appointment appointment = appointmentRepository.findLatestAppointmentByUser(userId);

		if (appointment == null) {
			return null; // No appointment found
		}

		Map<String, Object> appointmentDetails = new HashMap<>();
		appointmentDetails.put("id", appointment.getId());
		appointmentDetails.put("slot", appointment.getSlot());
		appointmentDetails.put("status", appointment.getStatus());
		appointmentDetails.put("date", appointment.getDate());
		appointmentDetails.put("doctorId", appointment.getDoctor().getId()); // Get doctor ID
		appointmentDetails.put("userId", appointment.getUser().getId()); // Get user ID

		return appointmentDetails;
	}

//	@Override
//	public Map<String, Object> getLatestAppointmentByUser(Integer userId) {
//		Appointment appointment = appointmentRepository.findTopByUserIdOrderByIdDesc(userId);
//
//		if (appointment == null) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment found");
//		}
//
//		// Create a response map with appointment details
//		Map<String, Object> response = new HashMap<>();
//		response.put("userName", appointment.getUser().getName());
//		response.put("doctorName", appointment.getDoctor().getName());
//		response.put("department", appointment.getDoctor().getDepartment().getName());
//		response.put("date", appointment.getSlot()); // Assuming slot represents date/time
//		response.put("status", appointment.getStatus());
//
//		return response;
//	}

	@Override
	public Appointment bookAppointment(AppointmentRequestDTO appointmentRequest) {
		try {
			// ✅ Log received request details
			System.out.println("Received Booking Request: " + appointmentRequest);

			// ✅ Validate Doctor
			Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId()).orElseThrow(
					() -> new RuntimeException("Doctor not found with ID: " + appointmentRequest.getDoctorId()));

			// ✅ Validate User
			User user = userRepository.findById(appointmentRequest.getUserId()).orElseThrow(
					() -> new RuntimeException("User not found with ID: " + appointmentRequest.getUserId()));

			// ✅ Validate and Convert Date
			if (appointmentRequest.getDate() == null || appointmentRequest.getDate().trim().isEmpty()) {
				throw new RuntimeException("Date is missing in request.");
			}

			// ✅ Try parsing the date correctly
			java.sql.Date appointmentDate;
			try {
				appointmentDate = java.sql.Date.valueOf(appointmentRequest.getDate()); // ✅ Convert String to SQL Date
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Invalid date format. Expected format: YYYY-MM-DD");
			}

			// ✅ Create new appointment
			Appointment appointment = new Appointment();
			appointment.setDoctor(doctor);
			appointment.setUser(user);
			appointment.setSlot(appointmentRequest.getSlot());
			appointment.setDate(appointmentDate); // ✅ Store formatted date
			appointment.setStatus("Booked");

			// ✅ Save to database
			return appointmentRepository.save(appointment);

		} catch (Exception e) {
			System.err.println("Error Booking Appointment: " + e.getMessage()); // ✅ Log error
			throw new RuntimeException("Failed to book appointment: " + e.getMessage());
		}
	}

	@Override
	public Appointment bookAppointmentWithEmail(String email, AppointmentRequestDTO appointmentRequest) {
		Integer userId = userService.getUserIdByEmail(email);
		if (userId == null) {
			throw new RuntimeException("User not found");
		}

		// ✅ Convert String date to SQL Date
		Date sqlDate = Date.valueOf(appointmentRequest.getDate());

		// Check if the slot is already booked for the doctor
		Long existingAppointments = appointmentRepository.countAppointmentsByDoctorAndSlot(
				appointmentRequest.getDoctorId(), sqlDate, // ✅ Pass Date instead of String
				appointmentRequest.getSlot());

		if (existingAppointments > 0) {
			throw new RuntimeException("This slot is already booked for the selected doctor.");
		}

		// Proceed with booking
		appointmentRequest.setUserId(userId);
		appointmentRequest.setDate(sqlDate.toString()); // Store back as String if needed
		return bookAppointment(appointmentRequest);
	}

	@Override
	public List<Map<String, Object>> getAppointmentsByDoctor(Integer doctorId) {
		List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

		List<Map<String, Object>> appointmentList = new ArrayList<>();
		for (Appointment appointment : appointments) {
			Map<String, Object> appointmentDetails = new HashMap<>();
			appointmentDetails.put("id", appointment.getId());
			appointmentDetails.put("slot", appointment.getSlot());
			appointmentDetails.put("status", appointment.getStatus());
			appointmentDetails.put("date", appointment.getDate());
			appointmentDetails.put("doctorId", appointment.getDoctor().getId()); // ✅ Fetch doctor ID correctly
			appointmentDetails.put("userId", appointment.getUser().getId()); // ✅ Fetch user ID correctly
			appointmentList.add(appointmentDetails);
		}
		return appointmentList;
	}

	@Override
	public List<String> getBookedSlots(Integer doctorId, String date) {
		Date sqlDate = Date.valueOf(date); // ✅ Convert String to SQL Date
		return appointmentRepository.getBookedSlotsForDoctor(doctorId, sqlDate);
	}

	@Override
	public AppointmentDTO getLatestAppointmentByUserDTO(Integer userId) {
		Appointment appointment = appointmentRepository.findTopByUserIdOrderByIdDesc(userId);

		if (appointment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment found");
		}

		return new AppointmentDTO(appointment.getDoctor().getId(), appointment.getUser().getName(),
				appointment.getDoctor().getName(), appointment.getDoctor().getDepartment().getName(),
				appointment.getDate(), appointment.getSlot(), appointment.getStatus());
	}

	@Override
	public List<AppointmentManageDTO> getAllAppointments() {
		List<Appointment> appointments = appointmentRepository.findAll(); // Replace with your repository call
		List<AppointmentManageDTO> appointmentDTOs = new ArrayList<>();

		for (Appointment appointment : appointments) {
			// Assuming Appointment has these properties: doctorId, userName, doctorName,
			// department, date, slot, status
			AppointmentManageDTO dto = new AppointmentManageDTO(appointment.getDoctor().getId(),
					appointment.getUser().getName(), appointment.getDoctor().getName(),
					appointment.getDoctor().getDepartment().getName(), appointment.getDate(), // You may want to format
																								// or adjust the date as
																								// needed
					appointment.getSlot(), appointment.getStatus(),appointment.getId());
			appointmentDTOs.add(dto);
		}

		return appointmentDTOs;
	}

	@Override
	public List<AppointmentDTO> getUserAppointments(Integer userId) {
		List<Appointment> appointments = appointmentRepository.findByUserId(userId);
		List<AppointmentDTO> appointmentDTOs = new ArrayList<>();

		for (Appointment appointment : appointments) {
			// Check if a review exists for this appointment
			Optional<Review> review = reviewRepository.findByAppointmentId(appointment.getId());

			// Create DTO with review details if available
			AppointmentDTO dto = new AppointmentDTO(appointment.getId(), appointment.getDoctor().getId(),
					appointment.getUser().getName(), appointment.getDoctor().getName(),
					appointment.getDoctor().getDepartment().getName(), appointment.getDate().toString(),
					appointment.getSlot(), appointment.getStatus(), review.isPresent(), // ✅ Mark if a review exists
					review.map(Review::getRating).orElse(null), // ✅ Get rating if exists
					review.map(Review::getComments).orElse(null) // ✅ Get comment if exists
			);

			appointmentDTOs.add(dto);
		}

		return appointmentDTOs;
	}

	@Override
	public Appointment findById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);  // Return null if not found
    }
	@Override
	public void save(Appointment appointment) {
        appointmentRepository.save(appointment);  // Save or update the appointment
    }

//	@Override
//	public List<AppointmentDTO> getUserAppointments(Integer userId) {
//		List<Appointment> appointments = appointmentRepository.findByUserId(userId);
//
//		if (appointments.isEmpty()) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointments found");
//		}
//
//		return appointments.stream().map(appointment -> {
//			boolean reviewExists = reviewRepository.existsByAppointmentId(appointment.getId());
//
//			return new AppointmentDTO(appointment.getId(), appointment.getDoctor().getId(),
//					appointment.getUser().getName(), appointment.getDoctor().getName(),
//					appointment.getDoctor().getDepartment().getName(), appointment.getDate(), appointment.getSlot(),
//					appointment.getStatus(), reviewExists);
//		}).collect(Collectors.toList());
//
//	}

//	@Override
//	public List<String> getBookedSlotsForDoctor(int doctorId, Date date) {
//        return appointmentRepository.findByDoctorIdAndDate(doctorId, date).stream()
//                .map(Appointment::getSlot)
//                .collect(Collectors.toList());
//    }

}

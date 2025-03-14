package com.example.Appointment.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorRepository;
import com.example.Appointment.User.User;
import com.example.Appointment.User.UserRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private UserRepository userRepository;

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
	public AppointmentDTO getLatestAppointmentByUser(Integer userId) {
		Appointment appointment = appointmentRepository.findTopByUserIdOrderByIdDesc(userId);

		if (appointment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment found");
		}

		return new AppointmentDTO(appointment.getUser().getName(), appointment.getDoctor().getName(),
				appointment.getDoctor().getDepartment().getName(), appointment.getDate(), appointment.getSlot(),
				appointment.getStatus());
	}

//	@Override
//	public List<String> getBookedSlotsForDoctor(int doctorId, Date date) {
//        return appointmentRepository.findByDoctorIdAndDate(doctorId, date).stream()
//                .map(Appointment::getSlot)
//                .collect(Collectors.toList());
//    }

}

package com.example.Appointment.Appointment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	public Map<String, Object> getLatestAppointmentByUser(Integer userId) {
		Appointment appointment = appointmentRepository.findTopByUserIdOrderByIdDesc(userId);

		if (appointment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appointment found");
		}

		// Create a response map with appointment details
		Map<String, Object> response = new HashMap<>();
		response.put("userName", appointment.getUser().getName());
		response.put("doctorName", appointment.getDoctor().getName());
		response.put("department", appointment.getDoctor().getDepartment().getName());
		response.put("date", appointment.getSlot()); // Assuming slot represents date/time
		response.put("status", appointment.getStatus());

		return response;
	}
}

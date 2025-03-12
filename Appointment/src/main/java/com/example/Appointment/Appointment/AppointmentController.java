package com.example.Appointment.Appointment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/confirmation/{userId}")
	public ResponseEntity<Map<String, Object>> getAppointmentConfirmation(@PathVariable Integer userId) {
		Map<String, Object> appointmentDetails = appointmentService.getLatestAppointmentByUser(userId);
		return ResponseEntity.ok(appointmentDetails);
	}
}

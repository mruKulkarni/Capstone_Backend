package com.example.Appointment.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@CrossOrigin("*")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/confirmation/{userId}")
	public AppointmentDTO getAppointmentConfirmation(@PathVariable Integer userId) {
		AppointmentDTO appointmentDetails = appointmentService.getLatestAppointmentByUser(userId);
		return appointmentDetails;
	}

	@PostMapping("/book")
	public Appointment bookAppointment(@RequestBody AppointmentRequestDTO appointmentRequest) {
		return appointmentService.bookAppointment(appointmentRequest);
	}
}

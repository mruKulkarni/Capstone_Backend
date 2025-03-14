package com.example.Appointment.Appointment;

import java.util.List;
import java.util.Map;

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
@CrossOrigin(origins="http://localhost:4200")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/confirmation/{userId}")
	public AppointmentDTO getAppointmentConfirmation(@PathVariable Integer userId) {
		AppointmentDTO appointmentDetails = appointmentService.getLatestAppointmentByUser(userId);
		return appointmentDetails;
	}

	@PostMapping("/book")
	public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequestDTO request) {
	    try {
	        Appointment appointment = appointmentService.bookAppointmentWithEmail(request.getEmail(), request);
	        return ResponseEntity.ok(Map.of("message", "Appointment booked successfully!", "appointmentId", appointment.getId()));
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
	    }
	}
	
	@GetMapping("/booked-slots/{doctorId}/{date}")
    public ResponseEntity<List<String>> getBookedSlots(@PathVariable Integer doctorId, @PathVariable String date) {
        List<String> bookedSlots = appointmentService.getBookedSlots(doctorId, date);
        return ResponseEntity.ok(bookedSlots);
    }
	public Appointment bookAppointment(@RequestBody AppointmentRequestDTO appointmentRequest) {
		return appointmentService.bookAppointment(appointmentRequest);
	}
}

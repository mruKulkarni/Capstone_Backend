package com.example.Appointment.Appointment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/confirmation/{userId}")
    public ResponseEntity<AppointmentDTO> getAppointmentConfirmation(@PathVariable Integer userId) {
        AppointmentDTO appointmentDetails = appointmentService.getLatestAppointmentByUserDTO(userId);
        return ResponseEntity.ok(appointmentDetails);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequestDTO request) {
        try {
            Appointment appointment = appointmentService.bookAppointmentWithEmail(request.getEmail(), request);
            return ResponseEntity
                    .ok(Map.of("message", "Appointment booked successfully!", "appointmentId", appointment.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/booked-slots/{doctorId}/{date}")
    public ResponseEntity<List<String>> getBookedSlots(@PathVariable Integer doctorId, @PathVariable String date) {
        List<String> bookedSlots = appointmentService.getBookedSlots(doctorId, date);
        return ResponseEntity.ok(bookedSlots);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentManageDTO>> getAllAppointments() {
        List<AppointmentManageDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}")
    public List<AppointmentDTO> getUserAppointments(@PathVariable Integer userId) {
        return appointmentService.getUserAppointments(userId);
    }

    // Endpoint to update the status of an appointment
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable("id") Integer id, @RequestBody Map<String, String> statusRequest) {
        // Find the appointment by ID
        Appointment appointment = appointmentService.findById(id);

        // If appointment is not found, return a 404 response
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the status of the appointment
        String newStatus = statusRequest.get("status");
        
        // Ensure that the status is either valid or set to 'Completed'
        if (newStatus == null || newStatus.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        // Optionally check if the current status allows the status update
        if ("Completed".equals(newStatus) && "Completed".equals(appointment.getStatus())) {
            return ResponseEntity.status(400).body(null); // Can't mark as completed if it's already completed
        }

        appointment.setStatus(newStatus);
        // Save the updated appointment
        appointmentService.save(appointment);

        // Return the updated appointment
        return ResponseEntity.ok(appointment);
    }
}

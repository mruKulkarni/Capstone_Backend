package com.example.Appointment.Appointment;

public interface AppointmentService {
	// Map<String, Object> getLatestAppointmentByUser(Integer userId);

	AppointmentDTO getLatestAppointmentByUser(Integer userId);

	Appointment bookAppointment(AppointmentRequestDTO appointmentRequest);

	// List<String> getBookedSlotsForDoctor(int doctorId, Date date);
}

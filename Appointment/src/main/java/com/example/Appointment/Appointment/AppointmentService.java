package com.example.Appointment.Appointment;

import java.util.Map;


public interface AppointmentService {
	Map<String, Object> getLatestAppointmentByUser(Integer userId);

	Appointment bookAppointment(AppointmentRequestDTO appointmentRequest);

	//List<String> getBookedSlotsForDoctor(int doctorId, Date date);
}

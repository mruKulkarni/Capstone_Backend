package com.example.Appointment.Appointment;

import java.util.Map;

public interface AppointmentService {
	Map<String, Object> getLatestAppointmentByUser(Integer userId);
}

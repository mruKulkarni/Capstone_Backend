package com.example.Appointment.Appointment;

import java.util.List;
import java.util.Map;

public interface AppointmentService {
	Map<String, Object> getLatestAppointmentByUser(Integer userId);

	AppointmentDTO getLatestAppointmentByUserDTO(Integer userId);

	Appointment bookAppointment(AppointmentRequestDTO appointmentRequest);

	Appointment bookAppointmentWithEmail(String email, AppointmentRequestDTO appointmentRequest);

	// List<String> getBookedSlotsForDoctor(int doctorId, Date date);

	public List<Map<String, Object>> getAppointmentsByDoctor(Integer doctorId);

	List<String> getBookedSlots(Integer doctorId, String date);
	// List<String> getBookedSlotsForDoctor(int doctorId, Date date);

	List<AppointmentManageDTO> getAllAppointments();

	List<AppointmentDTO> getUserAppointments(Integer userId);

	Appointment findById(Integer id);

	void save(Appointment appointment);

}

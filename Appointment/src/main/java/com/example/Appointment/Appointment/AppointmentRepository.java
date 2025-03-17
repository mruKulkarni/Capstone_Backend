package com.example.Appointment.Appointment;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	Appointment findTopByUserIdOrderByIdDesc(Integer userId);

	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 ORDER BY a.date DESC")
	List<Appointment> findByDoctorId(Integer doctorId);

	@Query("SELECT a.slot FROM Appointment a WHERE a.doctor.id = ?1 AND a.date = ?2")
	List<String> getBookedSlotsForDoctor(int doctorId, Date date);

	@Query("SELECT a FROM Appointment a WHERE a.user.id = ?1 ORDER BY a.date DESC LIMIT 1")
	Appointment findLatestAppointmentByUser(Integer userId);

	@Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = ?1 AND a.date = ?2 AND a.slot = ?3")
	Long countAppointmentsByDoctorAndSlot(Integer doctorId, Date date, String slot);

	List<Appointment> findAll();

	List<Appointment> findByUserId(Integer userId);
}

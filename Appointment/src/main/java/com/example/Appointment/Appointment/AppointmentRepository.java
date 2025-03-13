package com.example.Appointment.Appointment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	Appointment findTopByUserIdOrderByIdDesc(Integer userId);
	List<Appointment> findByDoctorId(int doctorId);
}

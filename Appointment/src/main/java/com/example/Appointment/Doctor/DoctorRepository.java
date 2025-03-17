package com.example.Appointment.Doctor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	Optional<Doctor> findByDoctorCode(String doctorCode);

	List<Doctor> findDoctorByDepartmentId(Integer departmentId);

	// Fetch only active doctors in a department
	List<Doctor> findDoctorByDepartmentIdAndIsActiveTrue(Integer departmentId);

	// Fetch only active doctors
	List<Doctor> findByIsActiveTrue();
}

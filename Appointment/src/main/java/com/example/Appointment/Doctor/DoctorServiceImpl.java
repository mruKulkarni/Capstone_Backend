package com.example.Appointment.Doctor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Department.DepartmentRepository;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	public Doctor addDoctor(DoctorDTO doctorDTO) {
		// Check if doctor with the same code already exists
		Optional<Doctor> existingDoctor = doctorRepository.findByDoctorCode(doctorDTO.getDoctorCode());
		if (existingDoctor.isPresent()) {
			throw new RuntimeException("Doctor with this code already exists.");
		}

		// Fetch department
		Department department = departmentRepository.findById(doctorDTO.getDepartmentId())
				.orElseThrow(() -> new RuntimeException("Department not found"));

		// Create new doctor entity
		Doctor doctor = new Doctor();
		doctor.setDoctorCode(doctorDTO.getDoctorCode());
		doctor.setName(doctorDTO.getName());
		doctor.setQualification(doctorDTO.getQualification());
		doctor.setAverageRating(doctorDTO.getAverageRating());
		doctor.setDepartment(department);

		return doctorRepository.save(doctor);
	}

}

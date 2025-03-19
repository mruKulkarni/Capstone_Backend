package com.example.Appointment.Doctor;

import java.util.List;

public interface DoctorService {
	Doctor addDoctor(DoctorDTO doctorDTO);

	List<Doctor> getDoctorsByDepartmentId(Integer id);

	boolean deleteDoctor(Integer id);

	Doctor updateDoctor(Integer id, UpdateDoctorDTO updateDoctorDTO);
}

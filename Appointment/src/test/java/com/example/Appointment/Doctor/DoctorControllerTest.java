package com.example.Appointment.Doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorController;
import com.example.Appointment.Doctor.DoctorDTO;
import com.example.Appointment.Doctor.DoctorService;
import com.example.Appointment.Doctor.UpdateDoctorDTO;

class DoctorControllerTest {

	@InjectMocks
	private DoctorController doctorController; // Controller being tested

	@Mock
	private DoctorService doctorService; // Mocked service layer

	private DoctorDTO doctorDTO;
	private UpdateDoctorDTO updateDoctorDTO;
	private Doctor doctor;
	private Department department;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks

		department = new Department(1, "Cardiology", null); // Sample department
		doctorDTO = new DoctorDTO("D123", "Dr. John Doe", "MBBS, MD", 5.0, 1);
		updateDoctorDTO = new UpdateDoctorDTO("Dr. John Doe Updated", "Neurology");
		doctor = new Doctor(1, "D123", "Dr. John Doe", "MBBS, MD", 5.0, null, department);
	}

	@Test
	void testAddDoctor() {
		when(doctorService.addDoctor(any(DoctorDTO.class))).thenReturn(doctor);

		ResponseEntity<Doctor> response = doctorController.addDoctor(doctorDTO);

		assertNotNull(response.getBody());
		assertEquals("Dr. John Doe", response.getBody().getName());
		assertEquals("Cardiology", response.getBody().getDepartment().getName());

		verify(doctorService, times(1)).addDoctor(any(DoctorDTO.class));
	}

	@Test
	void testDeleteDoctor_Success() {
		when(doctorService.deleteDoctor(1)).thenReturn(true);

		ResponseEntity<String> response = doctorController.deleteDoctor(1);

		assertEquals(200, response.getStatusCode().value()); // ✅ Updated line
		assertEquals("Doctor deleted successfully.", response.getBody());

		verify(doctorService, times(1)).deleteDoctor(1);
	}

	@Test
	void testDeleteDoctor_NotFound() {
		when(doctorService.deleteDoctor(999)).thenReturn(false);

		ResponseEntity<String> response = doctorController.deleteDoctor(999);

		assertEquals(404, response.getStatusCode().value()); // ✅ Updated line
		assertEquals("Doctor not found.", response.getBody());

		verify(doctorService, times(1)).deleteDoctor(999);
	}

	@Test
	void testUpdateDoctor() {
		Doctor updatedDoctor = new Doctor(1, "D123", "Dr. John Doe Updated", "Neurology", 4.8, null, department);
		when(doctorService.updateDoctor(eq(1), any(UpdateDoctorDTO.class))).thenReturn(updatedDoctor);

		ResponseEntity<Doctor> response = doctorController.updateDoctor(1, updateDoctorDTO);

		assertNotNull(response.getBody());
		assertEquals("Dr. John Doe Updated", response.getBody().getName());
		assertEquals("Neurology", response.getBody().getQualification());

		verify(doctorService, times(1)).updateDoctor(eq(1), any(UpdateDoctorDTO.class));
	}
}

package com.example.Appointment.Doctor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Department.DepartmentRepository;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorDTO;
import com.example.Appointment.Doctor.DoctorRepository;
import com.example.Appointment.Doctor.DoctorServiceImpl;
import com.example.Appointment.Doctor.UpdateDoctorDTO;

class DoctorServiceImplTest {

	@Mock
	private DoctorRepository doctorRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private DoctorServiceImpl doctorService;

	private Department department;
	private Doctor doctor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		department = new Department();
		department.setId(1);
		department.setName("Cardiology");

		doctor = new Doctor();
		doctor.setId(1);
		doctor.setDoctorCode("D123");
		doctor.setName("Dr. John Doe");
		doctor.setQualification("MBBS, MD");
		doctor.setAverageRating(4.5);
		doctor.setActive(true);
		doctor.setDepartment(department);
	}

	@Test
	void testAddDoctor_Success() {
		DoctorDTO doctorDTO = new DoctorDTO("D123", "Dr. John Doe", "MBBS, MD", 4.5, 1);
		when(doctorRepository.findByDoctorCode("D123")).thenReturn(Optional.empty());
		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

		Doctor savedDoctor = doctorService.addDoctor(doctorDTO);

		assertNotNull(savedDoctor);
		assertEquals("D123", savedDoctor.getDoctorCode());
		verify(doctorRepository, times(1)).save(any(Doctor.class));
	}

	@Test
	void testAddDoctor_DuplicateDoctorCode() {
		DoctorDTO doctorDTO = new DoctorDTO("D123", "Dr. John Doe", "MBBS, MD", 4.5, 1);
		when(doctorRepository.findByDoctorCode("D123")).thenReturn(Optional.of(doctor));

		Exception exception = assertThrows(RuntimeException.class, () -> doctorService.addDoctor(doctorDTO));
		assertEquals("Doctor with this code already exists.", exception.getMessage());
	}

	@Test
	void testGetDoctorsByDepartmentId() {
		when(doctorRepository.findDoctorByDepartmentIdAndIsActiveTrue(1)).thenReturn(Arrays.asList(doctor));

		List<Doctor> doctors = doctorService.getDoctorsByDepartmentId(1);

		assertThat(doctors).hasSize(1);
		assertEquals("Dr. John Doe", doctors.get(0).getName());
	}

	@Test
	void testDeleteDoctor_Success() {
		when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

		boolean result = doctorService.deleteDoctor(1);

		assertTrue(result);
		assertFalse(doctor.isActive());
		verify(doctorRepository, times(1)).save(doctor);
	}

	@Test
	void testDeleteDoctor_NotFound() {
		when(doctorRepository.findById(1)).thenReturn(Optional.empty());

		boolean result = doctorService.deleteDoctor(1);

		assertFalse(result);
	}

	@Test
	void testUpdateDoctor_Success() {
		UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO("Dr. Updated Name", "MBBS, MD, Cardiology");
		when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

		Doctor updatedDoctor = doctorService.updateDoctor(1, updateDoctorDTO);

		assertNotNull(updatedDoctor);
		assertEquals("Dr. Updated Name", updatedDoctor.getName());
		assertEquals("MBBS, MD, Cardiology", updatedDoctor.getQualification());
	}

	@Test
	void testUpdateDoctor_NotFound() {
		UpdateDoctorDTO updateDoctorDTO = new UpdateDoctorDTO("Dr. Updated Name", "MBBS, MD, Cardiology");
		when(doctorRepository.findById(1)).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class,
				() -> doctorService.updateDoctor(1, updateDoctorDTO));
		assertEquals("Doctor not found", exception.getMessage());
	}
}

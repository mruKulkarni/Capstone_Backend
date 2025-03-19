package com.example.Appointment.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Department.DepartmentRepository;
import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DoctorRepositoryTest {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	private Doctor doctor1;
	private Doctor doctor2;

	private Department savedDepartment;

	@BeforeEach
	public void setUp() {
		// Step 1: Create and save a department before each test
		Department department = new Department();
		department.setName("Cardiology");
		savedDepartment = departmentRepository.save(department);
	}

	@Test
	public void testSaveDoctor() {
		// Step 2: Create a Doctor with the saved Department
		Doctor doctor = new Doctor();
		doctor.setName("Dr. John Doe");
		doctor.setQualification("MBBS, MD");
		doctor.setAverageRating(4.5);
		doctor.setActive(true);
		doctor.setDepartment(savedDepartment); // Assign the saved department

		// Step 3: Save the doctor
		Doctor savedDoctor = doctorRepository.save(doctor);

		// Step 4: Assertions to verify the saved doctor
		assertNotNull(savedDoctor.getId(), "Doctor ID should not be null");
		assertNotNull(savedDoctor.getDepartment(), "Doctor must be linked to a department");
		assertEquals(savedDepartment.getId(), savedDoctor.getDepartment().getId(), "Department IDs should match");
	}

//	@Test
//	@Order(2)
//	@Rollback(false)
//	void testFindByDoctorCode() {
//		Optional<Doctor> foundDoctor = doctorRepository.findByDoctorCode("D123");
//		assertThat(foundDoctor).isPresent();
//		assertThat(foundDoctor.get().getDoctorCode()).isEqualTo("D123");
//	}
//
//	@Test
//	@Order(3)
//	@Rollback(false)
//	void testFindDoctorByDepartmentId() {
//		List<Doctor> doctors = doctorRepository.findDoctorByDepartmentId(1);
//		assertThat(doctors).hasSize(2);
//	}
//
//	@Test
//	@Order(4)
//	@Rollback(false)
//	void testFindDoctorByDepartmentIdAndIsActiveTrue() {
//		List<Doctor> activeDoctors = doctorRepository.findDoctorByDepartmentIdAndIsActiveTrue(1);
//		assertThat(activeDoctors).hasSize(1);
//		assertThat(activeDoctors.get(0).getDoctorCode()).isEqualTo("D123");
//	}
//
//	@Test
//	@Order(5)
//	@Rollback(false)
//	void testFindByIsActiveTrue() {
//		List<Doctor> activeDoctors = doctorRepository.findByIsActiveTrue();
//		assertThat(activeDoctors).hasSize(1);
//		assertThat(activeDoctors.get(0).getDoctorCode()).isEqualTo("D123");
//	}
//
//	@Test
//	@Order(6)
//	@Rollback(false)
//	void testUpdateDoctor() {
//		Optional<Doctor> doctor = doctorRepository.findByDoctorCode("D123");
//		assertThat(doctor).isPresent();
//		doctor.get().setName("Dr. Updated Name");
//		doctorRepository.save(doctor.get());
//
//		Optional<Doctor> updatedDoctor = doctorRepository.findByDoctorCode("D123");
//		assertThat(updatedDoctor).isPresent();
//		assertThat(updatedDoctor.get().getName()).isEqualTo("Dr. Updated Name");
//	}
//
//	@Test
//	@Order(7)
//	@Rollback(false)
//	void testDeleteDoctor() {
//		doctorRepository.deleteById(doctor1.getId());
//		Optional<Doctor> deletedDoctor = doctorRepository.findById(doctor1.getId());
//		assertThat(deletedDoctor).isEmpty();
//	}
}

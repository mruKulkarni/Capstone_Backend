package com.example.Appointment.Doctor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

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

		// Step 2: Clear existing doctors to prevent duplicate constraint issues
		doctorRepository.deleteAll();

	}

	@Test
	@Order(1)
	@Rollback(false)
	public void testSaveDoctor() {
		// Step 2: Create a Doctor with the saved Department
		Doctor doctor = new Doctor();
		doctor.setDoctorCode("D123");
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

	@Test
	@Order(2)
	@Rollback(false)
	void testFindByDoctorCode() {
		Optional<Doctor> existingDoctor = doctorRepository.findByDoctorCode("D123");

		if (existingDoctor.isEmpty()) { // Avoid inserting duplicate doctor
			Doctor doctor = new Doctor();
			doctor.setDoctorCode("D123");
			doctor.setName("Dr. John Doe");
			doctor.setQualification("MBBS, MD");
			doctor.setAverageRating(4.5);
			doctor.setActive(true);
			doctor.setDepartment(savedDepartment);
			doctorRepository.save(doctor);
		}

		Optional<Doctor> foundDoctor = doctorRepository.findByDoctorCode("D123");
		assertThat(foundDoctor).isPresent();
		assertThat(foundDoctor.get().getDoctorCode()).isEqualTo("D123");
	}

	@Test
	@Order(3)
	@Rollback(false)
	void testFindDoctorByDepartmentId() {
		// Ensure two doctors exist before querying
		Doctor doctor1 = new Doctor();
		doctor1.setDoctorCode("D125");
		doctor1.setName("Dr. John Doe");
		doctor1.setQualification("MBBS, MD");
		doctor1.setAverageRating(4.5);
		doctor1.setActive(true);
		doctor1.setDepartment(savedDepartment);
		doctorRepository.save(doctor1);

		Doctor doctor2 = new Doctor();
		doctor2.setDoctorCode("D126");
		doctor2.setName("Dr. Jane Smith");
		doctor2.setQualification("MBBS, MD, Cardiology");
		doctor2.setAverageRating(4.8);
		doctor2.setActive(true);
		doctor2.setDepartment(savedDepartment);
		doctorRepository.save(doctor2);

		// Fetch doctors by department
		List<Doctor> doctors = doctorRepository.findDoctorByDepartmentId(savedDepartment.getId());

		// Assertions
		assertThat(doctors).hasSize(2); // ✅ Now expects exactly 2 doctors
	}

	@Test
	@Order(4)
	@Rollback(false)
	void testFindDoctorByDepartmentIdAndIsActiveTrue() {
		Doctor doctor1 = new Doctor();
		doctor1.setDoctorCode("D127");
		doctor1.setName("Dr. John Doe");
		doctor1.setQualification("MBBS, MD");
		doctor1.setAverageRating(4.5);
		doctor1.setActive(true);
		doctor1.setDepartment(savedDepartment);
		doctorRepository.save(doctor1);
		// List<Doctor> activeDoctors =
		// doctorRepository.findDoctorByDepartmentIdAndIsActiveTrue(1);
		List<Doctor> activeDoctors = doctorRepository.findDoctorByDepartmentIdAndIsActiveTrue(savedDepartment.getId());
		assertThat(activeDoctors).hasSize(1);
		assertThat(activeDoctors.get(0).getDoctorCode()).isEqualTo("D127");
	}

	@Test
	@Order(5)
	@Rollback(false)
	void testFindByIsActiveTrue() {
		Doctor doctor1 = new Doctor();
		doctor1.setDoctorCode("D128");
		doctor1.setName("Dr. John Doe");
		doctor1.setQualification("MBBS, MD");
		doctor1.setAverageRating(4.5);
		doctor1.setActive(true);
		doctor1.setDepartment(savedDepartment);
		doctorRepository.save(doctor1);
		List<Doctor> activeDoctors = doctorRepository.findByIsActiveTrue();
		assertThat(activeDoctors).hasSize(1);
		assertThat(activeDoctors.get(0).getDoctorCode()).isEqualTo("D128");
	}

	@Test
	@Order(6)
	@Rollback(false)
	void testUpdateDoctor() {
		Doctor doctor = new Doctor();
		doctor.setDoctorCode("D123");
		doctor.setName("Dr. Original Name");
		doctor.setQualification("MBBS, MD");
		doctor.setAverageRating(4.5);
		doctor.setActive(true);
		doctor.setDepartment(savedDepartment);
		doctorRepository.save(doctor); // Ensure doctor exists

		Optional<Doctor> existingDoctor = doctorRepository.findByDoctorCode("D123");
		assertThat(existingDoctor).isPresent(); // Ensure we actually found it

		existingDoctor.get().setName("Dr. Updated Name");
		doctorRepository.save(existingDoctor.get());

		Optional<Doctor> updatedDoctor = doctorRepository.findByDoctorCode("D123");
		assertThat(updatedDoctor).isPresent();
		assertThat(updatedDoctor.get().getName()).isEqualTo("Dr. Updated Name");
	}

//	@Test
//	@Order(7)
//	@Rollback(false)
//	void testDeleteDoctor() {
//		assertNotNull(doctor1, "doctor1 should not be null");
//		doctorRepository.deleteById(doctor1.getId());
//		Optional<Doctor> deletedDoctor = doctorRepository.findById(doctor1.getId());
//		assertThat(deletedDoctor).isEmpty();
//	}

}

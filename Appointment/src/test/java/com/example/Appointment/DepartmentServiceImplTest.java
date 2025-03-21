package com.example.Appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Appointment.Department.Department;
import com.example.Appointment.Department.DepartmentRepository;
import com.example.Appointment.Department.DepartmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {
	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private DepartmentServiceImpl departmentService;

	private Department department1;
	private Department department2;

	@BeforeEach
	void setUp() {
		department1 = new Department();
		department1.setId(1);
		department1.setName("Cardiology");

		department2 = new Department();
		department2.setId(2);
		department2.setName("Neurology");
	}

	@Test
	void testGetAllDepartments() {
		when(departmentRepository.findAllWithDoctors()).thenReturn(Arrays.asList(department1, department2));

		Collection<Department> result = departmentService.getAllDepartments();

		assertEquals(2, result.size());
		verify(departmentRepository, times(1)).findAllWithDoctors();
	}

	@Test
	void testAddNewDepartment() {
		when(departmentRepository.save(department1)).thenReturn(department1);

		Department result = departmentService.addNewDepartment(department1);

		assertNotNull(result);
		assertEquals(department1.getId(), result.getId());
		verify(departmentRepository, times(1)).save(department1);
	}

	@Test
	void testGetDepartmentById() {
		when(departmentRepository.findById(1)).thenReturn(Optional.of(department1));

		Optional<Department> result = departmentService.getDepartmentById(1);

		assertTrue(result.isPresent());
		assertEquals(department1.getId(), result.get().getId());
		verify(departmentRepository, times(1)).findById(1);
	}

	@Test
	void testGetDepartmentsWithActiveDoctors() {
		when(departmentRepository.findDepartmentsWithActiveDoctors()).thenReturn(Arrays.asList(department1));

		Collection<Department> result = departmentService.getDepartmentsWithActiveDoctors();

		assertEquals(1, result.size());
		verify(departmentRepository, times(1)).findDepartmentsWithActiveDoctors();
	}
}

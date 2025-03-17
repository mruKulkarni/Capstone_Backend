package com.example.Appointment.Department;

import java.util.Collection;
import java.util.Optional;

public interface DepartmentService {
	Collection<Department> getAllDepartments();

	Department addNewDepartment(Department department);

	Optional<Department> getDepartmentById(int id);
	
	Collection<Department> getDepartmentsWithActiveDoctors();
	
	
}

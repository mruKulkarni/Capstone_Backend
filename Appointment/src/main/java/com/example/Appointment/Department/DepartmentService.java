package com.example.Appointment.Department;

import java.util.Collection;

public interface DepartmentService {
	Collection<Department> getAllDepartments();

	Department addNewDepartment(Department department);
}

package com.example.Appointment.Department;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Collection<Department> getAllDepartments() {
		return this.departmentRepository.findAll();
	}

	@Override
	public Department addNewDepartment(Department department) {
		
		return departmentRepository.save(department);
	}

}

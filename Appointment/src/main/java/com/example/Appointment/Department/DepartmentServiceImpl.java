package com.example.Appointment.Department;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public Collection<Department> getAllDepartments() {
		return this.departmentRepository.findAllWithDoctors();
	}

	@Override
	public Department addNewDepartment(Department department) {
		
		return departmentRepository.save(department);
	}

	@Override
	public Optional<Department> getDepartmentById(int id) {
		 return departmentRepository.findById(id);
	}

	@Override
	public Collection<Department> getDepartmentsWithActiveDoctors() {
		return departmentRepository.findDepartmentsWithActiveDoctors();
	}

}

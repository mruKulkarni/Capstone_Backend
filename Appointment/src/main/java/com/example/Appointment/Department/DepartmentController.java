package com.example.Appointment.Department;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/departments")
	public Collection<Department> getAllDepartments(){
		return departmentService.getAllDepartments();
	}
	@PostMapping("/addDepartment")
	public Department addDepartment(@RequestBody Department department) {
		return this.departmentService.addNewDepartment(department);
	}
}

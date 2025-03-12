package com.example.Appointment.Department;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.Appointment.Doctor.Doctor;
import com.example.Appointment.Doctor.DoctorService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/departments")
	public Collection<Department> getAllDepartments(){
		return departmentService.getAllDepartments();
	}
	@PostMapping("/addDepartment")
	public Department addDepartment(@RequestBody Department department) {
		return this.departmentService.addNewDepartment(department);
	}
	
	@GetMapping("/departments/{id}/doctors")
	public List<Doctor> getDoctorsByDepartment(@PathVariable Integer id){
		return this.doctorService.getDoctorsByDepartmentId(id);
	}
}

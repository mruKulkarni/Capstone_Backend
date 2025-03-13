package com.example.Appointment.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
@CrossOrigin("*")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;

	@PostMapping
	public ResponseEntity<Doctor> addDoctor(@RequestBody DoctorDTO doctorDTO) {
		Doctor savedDoctor = doctorService.addDoctor(doctorDTO);
		return ResponseEntity.ok(savedDoctor);
	}
	
	

}

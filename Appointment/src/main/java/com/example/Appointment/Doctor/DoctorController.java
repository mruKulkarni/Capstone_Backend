package com.example.Appointment.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/{id}/deactivate")
	public ResponseEntity<String> deleteDoctor(@PathVariable Integer id) {
		boolean deleted = doctorService.deleteDoctor(id);
		if (deleted) {
			return ResponseEntity.ok("Doctor deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer id, @RequestBody UpdateDoctorDTO updateDoctorDTO) {
		Doctor updatedDoctor = doctorService.updateDoctor(id, updateDoctorDTO);
		return ResponseEntity.ok(updatedDoctor);
	}

}

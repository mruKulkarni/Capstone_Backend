package com.example.Appointment.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Object user = loginService.login(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user); // Return student or company details
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }
}

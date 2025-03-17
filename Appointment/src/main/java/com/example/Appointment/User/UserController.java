package com.example.Appointment.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/{email}")
	public User getUserByEmail(@PathVariable("email") String email) {
		return this.userService.getByEmail(email);
	}

	@PostMapping(value = "/register")
	public User registerUser(@Valid @RequestBody User user) {

		return userService.createUser(user);
	}

//	@PatchMapping("/update/{email}")
//	public User updateUser(@RequestBody User updatedUser, @PathVariable("email") String email) {
//		return this.userService.updateUser(updatedUser, email); 
//	}

	@GetMapping("/id")
	public Integer getUserId(@RequestBody String email) {
		return this.userService.getUserIdByEmail(email);
	}	
	
	@PatchMapping("user/update/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @PathVariable("email") String email) {
        try {
            User user = userService.updateUser(updatedUser, email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

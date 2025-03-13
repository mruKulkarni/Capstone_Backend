package com.example.Appointment.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{email}")
	public User getUserByEmail(@PathVariable("email")String email){
		return this.userService.getByEmail(email);
	}
	@PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
    	
        return userService.createUser(user);
    }
	  
	
//	@PatchMapping("/update/{email}")
//	public User updateUser(@RequestBody User updatedUser, @PathVariable("email") String email) {
//		return this.userService.updateUser(updatedUser, email); 
//	}
}

package com.example.Appointment.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Appointment.User.User;
import com.example.Appointment.User.UserRepository;

@Service
public class LoginServiceImpl  implements LoginService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public Object login(String email, String password) {
	
		// Check student record
        User user = userRepository.findByEmail(email);
        // Check company record
      

        // Ensure email is unique across both tables
//        if (student != null && company != null) {
//            throw new IllegalStateException("Error: Duplicate email found in both student and company records!");
        

        // If email belongs to a student
        if (user != null) {
            return user.getPassword().equals(password) ? user : null;
        }

        // If email belongs to a company
//        if (company != null) {
//            return company.getPassword().equals(password) ? company : null;
//        }

        return null; // No user found with given email
    }
	
	
	}



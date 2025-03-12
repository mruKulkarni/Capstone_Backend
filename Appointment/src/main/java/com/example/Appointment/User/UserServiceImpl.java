package com.example.Appointment.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	
	@Override
	public User registerUser(UserDTO userDto) {
		if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        }
       return userRepository.save(userDto);
	}

	@Override
	public User createUser(@Valid User user) {
		
		return userRepository.save(user);
	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean emailExists(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkCredentials(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

	

}

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

//	@Override
//	public User updateUser(User updatedUser) {
//		return this.userRepository/;
//	}

	@Override
	public User getByEmail(String email) {
		return this.userRepository.findByEmail(email);
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

	@Override
	public Integer getUserIdByEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		return(user !=null) ? user.getId(): null;
	}

	@Override
	public User updateUser(User updatedUser, String email) {
		User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        // Update only the fields that are provided
        if (updatedUser.getName() != null) {
			existingUser.setName(updatedUser.getName());
		}
        if (updatedUser.getPhone() != null) {
			existingUser.setPhone(updatedUser.getPhone());
		}

        return userRepository.save(existingUser);
	}

}

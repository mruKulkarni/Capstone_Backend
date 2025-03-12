package com.example.Appointment.User;

import jakarta.validation.Valid;

public interface UserService {

	User getByEmail(String email);
	User registerUser(UserDTO userDto);

	User createUser(@Valid User user);
//	User updateUser(User updatedUser, String email);

}

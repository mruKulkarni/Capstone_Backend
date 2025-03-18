package com.example.Appointment.User;

import jakarta.validation.Valid;

public interface UserService {

	User getByEmail(String email);
	User registerUser(UserDTO userDto);

	User createUser(@Valid User user);
	boolean emailExists(String email);
	boolean checkCredentials(String email, String password);
//	User updateUser(User updatedUser, String email);
	
	public Integer getUserIdByEmail(String email) ;
	public User updateUser(User updatedUser, String email);
	

}

package com.example.Appointment.User;

public class UserDTO {
	private String email;
	private String password;
	public UserDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public UserDTO() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

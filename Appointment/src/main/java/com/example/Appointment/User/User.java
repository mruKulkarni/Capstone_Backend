package com.example.Appointment.User;

import java.time.LocalDate; // Import LocalDate for Date of Birth
import java.util.List;

import com.example.Appointment.Appointment.Appointment;
import com.example.Appointment.Review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @Column(name = "date_of_birth") // Column name for the date of birth
    private LocalDate dateOfBirth; // Changed to LocalDate for Date of Birth
    
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String password;
    private String gender;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Appointment> appointments;

    public User() {
        super();
    }

    public User(Integer id, String name, LocalDate dateOfBirth, String email, String phone, String password, String gender,
            List<Review> reviews, List<Appointment> appointments) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.reviews = reviews;
        this.appointments = appointments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth; // Getter for dateOfBirth
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth; // Setter for dateOfBirth
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}

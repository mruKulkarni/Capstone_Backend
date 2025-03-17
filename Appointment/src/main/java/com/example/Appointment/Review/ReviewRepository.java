package com.example.Appointment.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	boolean existsByAppointmentId(Integer appointmentId);

	List<Review> findByDoctorId(Integer doctorId);

	List<Review> findByUserId(Integer userId);
}

package com.example.Appointment.Department;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>  {
	@Query("SELECT d FROM Department d WHERE EXISTS (SELECT doc FROM Doctor doc WHERE doc.department = d AND doc.isActive = true)")
	List<Department> findDepartmentsWithActiveDoctors();
	
	@Query("SELECT d FROM Department d LEFT JOIN FETCH d.doctors")
	List<Department> findAllWithDoctors();
}

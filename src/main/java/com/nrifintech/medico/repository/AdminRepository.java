package com.nrifintech.medico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nrifintech.medico.entity.Admin;
import com.nrifintech.medico.entity.Doctor;

import com.nrifintech.medico.response.GraphResponse;

@Repository
public interface AdminRepository extends IAbstractBaseRepository<Admin, Long> {
	Admin findByUsernameAndPassword(String username, String password);

	@Query(value = "Select d.specialization as label, COUNT(*) as y FROM  appointment a, doctor d WHERE a.doctor_id = d.doctor_id AND year(a.appt_date)=? GROUP BY d.specialization ", nativeQuery = true)
	List<GraphResponse> countAppointmentsBySpecializaton(String year);
}
package com.nrifintech.medico.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.nrifintech.medico.entity.Patient;


@Repository
public interface PatientRepository extends IAbstractBaseRepository<Patient, Long> {
	Patient findByUsernameAndPassword(String email, String password);
	Patient findByUsername(String email);
	
	@Query(value ="UPDATE patient SET email = ?, name = ?, password = ?, phone_no = ? WHERE patient_id = ?",nativeQuery = true)
	void updateProfile(String email, String name, String password, String phone_no, Long patient_id);
    
	@Modifying
	@Query(value = "UPDATE patient SET password = ? where email = ?", nativeQuery = true)
	void updatePassword(String new_password, String email);
    
	@Modifying
    @Query(value = "UPDATE patient SET name = ? where email = ?", nativeQuery = true)
	void updatePatientName(String name, String email);
    
	@Modifying
    @Query(value = "UPDATE patient SET phone_no = ? where email = ?", nativeQuery = true)
	void updatePatientPhone(String number, String email);
    
    @Query(value = "select password from patient where email = :email", nativeQuery = true)
	String getCurrentPassword(String email);
	
}

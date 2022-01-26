package com.nrifintech.medico.service;

import java.sql.Date;
import java.util.List;

import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.request.DoctorRegisterRequest;
import com.nrifintech.medico.request.EditRegisterRequest;
import com.nrifintech.medico.request.PatientRegisterRequest;
import com.nrifintech.medico.request.UserLoginRequest;

public interface DoctorService extends IAbstractBaseService<Doctor, Long> {
	public boolean isValidUser(UserLoginRequest userLoginRequest);
	
	public List<Doctor> findAllByNameAndUpvotes();
	
	public List<Doctor> filterDoctor(String searchParam, String searchByOption, String radioOption);
	public String isValidRegistration(DoctorRegisterRequest doctorRegisterRequest);
	public String isValidEdit(EditRegisterRequest editRegisterRequest);
	public void editDetails(String name,int fees,Date practice_started,String degree,String specialization,String password,String descr,String username);

}

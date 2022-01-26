package com.nrifintech.medico.service;

import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.request.PatientRegisterRequest;
import com.nrifintech.medico.request.UserLoginRequest;


public interface PatientService extends IAbstractBaseService<Patient, Long> {
	public boolean isValidUser(UserLoginRequest userLoginRequest);
	
	public String isValidRegistration(PatientRegisterRequest registerRequest);
	
	public String editProfile(String username, String name, String password, String confirmPassword, String phone_no);

	public String editName(String username, String name);

	public String editPhone_no(String username, String number);

	public String editPassword(String username, String cur_password, String new_password, String confirm_password);
}

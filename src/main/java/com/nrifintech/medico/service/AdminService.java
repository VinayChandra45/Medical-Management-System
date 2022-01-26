package com.nrifintech.medico.service;

import java.util.List;

import com.nrifintech.medico.entity.Admin;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.request.UserLoginRequest;

public interface AdminService extends IAbstractBaseService<Admin, Long> {
	public boolean isValidUser(UserLoginRequest userLoginRequest);

	public List<Object> getSpecializationCount();
	
	public List<Object> getMonthlyCount(String year);
	
	public List<Object> getAppointmentsBySpecialization(String year);
}

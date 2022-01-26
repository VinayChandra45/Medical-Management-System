package com.nrifintech.medico.service.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.repository.PatientRepository;
import com.nrifintech.medico.request.PatientRegisterRequest;
import com.nrifintech.medico.request.UserLoginRequest;
import com.nrifintech.medico.service.PatientService;
import com.nrifintech.medico.utility.CredentialFormatValidator;

@Service
@Transactional
public class PatientServiceImpl extends AbstractBaseServiceImpl<Patient, Long> implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private CredentialFormatValidator cfv;
	
	public PatientServiceImpl(PatientRepository patientRepository, CredentialFormatValidator cfv) {
		super(patientRepository);
		this.patientRepository = patientRepository;
		this.cfv = cfv;
	}

	@Override
	public boolean isValidUser(UserLoginRequest userLoginRequest) {
		
		if(Objects.isNull(userLoginRequest.getUsername()) || Objects.isNull(userLoginRequest.getPassword())) {
			return false;
		}
		
		Patient fetchedUser = this.patientRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());
		System.out.println(fetchedUser);
		if(!Objects.isNull(fetchedUser)) return true;
		else return false;
	}
	
	@Override
	public String isValidRegistration(PatientRegisterRequest patientRegisterRequest)
	{
		String name = patientRegisterRequest.getName();
		String username = patientRegisterRequest.getUsername().toLowerCase();
		String phone_no = patientRegisterRequest.getPhone_no();
		String password = patientRegisterRequest.getPassword();
		String confirm_password = patientRegisterRequest.getConfirm_password();
		
		// Check validity
		if (!cfv.isNotBlank(name))
			return "0 Name cannot be blank";
		if (!cfv.isNotBlank(phone_no))
			return "0 Phone No. cannot be blank";
		if (!cfv.isValidEmail(username))
			return "0 Invalid Email Format";
		if (!cfv.isValidPhone(phone_no))
			return "0 Invalid Phone Number";
		if (!cfv.passwordValidator(password))
			return "0 Invalid Password Format<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
//		if (!cfv.passwordValidator(confirm_password))
//			return "0 Invalid Confirm Password Field<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if(!password.equals(confirm_password))
				return "0 Password and Confirm Password Fields donot match";
		//check presence in db
		Patient fetchedUser  = patientRepository.findByUsername(patientRegisterRequest.getUsername());
		if(fetchedUser!=null)
		{
			return "0 User already exists, try logging in";
		}
		return "1 Success";
	}

	@Override
	public String editProfile(String username, String name, String password, String confirmPassword, String phone_no) {
		// Check validity
		// TODO
		if (!cfv.isValidEmail(username))
			return "0 Invalid Email Format";
		if (!cfv.isValidPhone(phone_no))
			return "0 Invalid Phone Number";
		if (!cfv.passwordValidator(password))
			return "0 Invalid Password Format<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if (!cfv.passwordValidator(confirmPassword))
			return "0 Invalid Confirm Password Field<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if(!password.equals(confirmPassword))
				return "0 Password and Confirm Password Fields donot match";
		//this.patientRepository.updateProfile(username, name, confirmPassword, phone_no, null);
		return "";
	}

	@Override
	public String editName(String username, String name) {
		//System.out.println("Inside editName " + username + name);
		if (!cfv.isNotBlank(name))
			return "Name cannot be blank";
		this.patientRepository.updatePatientName(name, username);
		return "";
	}

	@Override
	public String editPhone_no(String username, String number) {
		if (!cfv.isValidPhone(number))
			return "Invalid Phone Number";
		this.patientRepository.updatePatientPhone(number, username);
		return "";
	}

	@Override
	public String editPassword(String username, String cur_password, String new_password, String confirm_password) {
		// check if the cur_password is correct
		String curr = this.patientRepository.getCurrentPassword(username);
		if (!curr.equals(cur_password))
			return "Enter correct Password";
		// new password
		if (!cfv.passwordValidator(new_password))
			return "Invalid Password Format<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if(!new_password.equals(confirm_password))
				return "Password and Confirm Password Fields donot match";
		this.patientRepository.updatePassword(new_password, username);
		return "";
	}
}

package com.nrifintech.medico.service.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.repository.DoctorRepository;
import com.nrifintech.medico.request.DoctorRegisterRequest;
import com.nrifintech.medico.request.EditRegisterRequest;
import com.nrifintech.medico.request.UserLoginRequest;
import com.nrifintech.medico.service.DoctorService;
import com.nrifintech.medico.utility.CredentialFormatValidator;


@Service
@Transactional
public class DoctorServiceImpl extends AbstractBaseServiceImpl<Doctor, Long> implements DoctorService{
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private CredentialFormatValidator cfv;
	
	public DoctorServiceImpl(DoctorRepository doctorRepository, CredentialFormatValidator cfv) {
		super(doctorRepository);
		this.doctorRepository = doctorRepository;
		this.cfv = cfv;
	}

	@Override
	public boolean isValidUser(UserLoginRequest userLoginRequest) {
		// TODO Auto-generated method stub
		if(Objects.isNull(userLoginRequest.getUsername()) || Objects.isNull(userLoginRequest.getPassword())) {
			return false;
		}
		
		Doctor fetchedUser = doctorRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());
		System.out.println(fetchedUser);
		if(!Objects.isNull(fetchedUser)) return true;
		else return false;
	}
	
	@Override
	public List<Doctor> findAllByNameAndUpvotes() {
		return doctorRepository.findByOrderByNameAscOrderByUpvotesDesc();
	}
	
	@Override
	public List<Doctor> filterDoctor(String searchParam, String searchByOption, String radioOption) {
		
		if (searchByOption.equals("specialization")) {
			//if (radioOption == null) return doctorRepository.find
			if (radioOption == null || radioOption.equals("fees")) return doctorRepository.findBySpecializationOrderByFeesAsc(searchParam);
			else return doctorRepository.findBySpecializationOrderByUpvotesDesc(searchParam); 
		}
		else {
			// System.out.println("in service " + radioOption);
			if (radioOption == null || radioOption.equals("fees")) return doctorRepository.findByNameOrderByFeesAsc(searchParam);
			else return doctorRepository.findByNameOrderByUpvotesDesc(searchParam);
		}
	}

	@Override
	public String isValidRegistration(DoctorRegisterRequest doctorRegisterRequest) {
		String name = doctorRegisterRequest.getName().toUpperCase();
		String username = doctorRegisterRequest.getUsername().toLowerCase();
		String password = doctorRegisterRequest.getPassword();
		String degree = doctorRegisterRequest.getDegree();
		String specialization = doctorRegisterRequest.getSpecialization();
		String descr = doctorRegisterRequest.getDescr();
		Date date = doctorRegisterRequest.getPractice_started();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
		String strDate = dateFormat.format(date); 
		// Check validity
		if (!cfv.isNotBlank(name))
			return "0 Name cannot be blank";
		if (!cfv.isValidEmail(username))
			return "0 Invalid Email Format";
		if (!cfv.passwordValidator(password))
			return "0 Invalid Password Format<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if (!cfv.isNotBlank(strDate))
			return "0 Date cannot be blank";
		if (!cfv.isNotBlank(degree))
			return "0 Degree cannot be blank";
		if (!cfv.isNotBlank(specialization))
			return "0 Specialization cannot be blank";
		if (!cfv.isNotBlank(descr))
			return "0 Description cannot be blank";
		
		if(!(name.substring(0,3).equals("DR.")))
			name = "DR. "+name;
		
		doctorRegisterRequest.setName(name);
		//check presence in db
		Doctor fetchedDoctor  = doctorRepository.findByUsername(doctorRegisterRequest.getUsername());
		if(fetchedDoctor!=null)
		{
			return "0 Doctor already exists.";
		}
		return "1 Successfully added doctor.";
		
	}
	@Override
	public String isValidEdit(EditRegisterRequest editRegisterRequest) {
		String name = editRegisterRequest.getName().toUpperCase();
		String username = editRegisterRequest.getUsername().toLowerCase();
		String password = editRegisterRequest.getPassword();
		String degree = editRegisterRequest.getDegree();
		String specialization = editRegisterRequest.getSpecialization();
		String descr = editRegisterRequest.getDescr();
		Date date = editRegisterRequest.getPractice_started();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
		String strDate = dateFormat.format(date); 
		// Check validity
		if (!cfv.isNotBlank(name))
			return "0 Name cannot be blank";
		if (!cfv.isValidEmail(username))
			return "0 Invalid Email Format";
		if (!cfv.passwordValidator(password))
			return "0 Invalid Password Format<br>Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _";
		if (!cfv.isNotBlank(strDate))
			return "0 Date cannot be blank";
		if (!cfv.isNotBlank(degree))
			return "0 Degree cannot be blank";
		if (!cfv.isNotBlank(specialization))
			return "0 Specialization cannot be blank";
		if (!cfv.isNotBlank(descr))
			return "0 Description cannot be blank";
		
		if(!(name.substring(0,3).equals("DR.")))
			name = "DR. "+name;
		
		editRegisterRequest.setName(name);
		
		return "1 Successfully added doctor.";
	}
	
	public void editDetails(String name,int fees,Date practice_started,String degree,String specialization,String password,String descr,String username) {
		doctorRepository.updateDetails(name, fees, practice_started, degree, specialization, password, descr,username);
	}

}
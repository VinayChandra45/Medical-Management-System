package com.nrifintech.medico.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.request.PatientRegisterRequest;
import com.nrifintech.medico.service.PatientService;

@Controller
public class RegisterController
{

	private final PatientService patientService;
	
	@Autowired
	public RegisterController(PatientService patientService) {
		this.patientService = patientService;
	}



	@RequestMapping("/register")
	public String register()
	{
		//System.out.println("hey");
		return "register";
	}
	
	@PostMapping("/doRegister")
	public String doRegister(PatientRegisterRequest patientRegisterRequest, HttpServletRequest request, Model model) {
		String result = patientService.isValidRegistration(patientRegisterRequest);
		boolean isValidRegistration = result.charAt(0)=='1';
		
		if(isValidRegistration) {
			Patient registering_patient = new Patient();
			registering_patient.setName(patientRegisterRequest.getName().toUpperCase());
			registering_patient.setusername(patientRegisterRequest.getUsername().toLowerCase());
			registering_patient.setPassword(patientRegisterRequest.getPassword());
			registering_patient.setPhoneNumber(patientRegisterRequest.getPhone_no());
			patientService.save(registering_patient);
			System.out.println("Register request"+patientRegisterRequest);
			model.addAttribute("registered", "Registration Successful");
			return "forward:/register";
		}
		else
		{
			model.addAttribute("error", result.substring(2));
			return "forward:/register";
		}
	}
	
}

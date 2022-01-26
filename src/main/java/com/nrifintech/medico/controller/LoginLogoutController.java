package com.nrifintech.medico.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nrifintech.medico.request.UserLoginRequest;
import com.nrifintech.medico.service.DoctorService;
import com.nrifintech.medico.service.PatientService;
import com.nrifintech.medico.utility.CredentialFormatValidator;
import com.nrifintech.medico.service.AdminService;


@Controller
public class LoginLogoutController {
	
	private final PatientService patientService;
	private final DoctorService doctorService;
	private final AdminService adminService;
	private CredentialFormatValidator cfv;
	
	@Autowired
	public LoginLogoutController(PatientService patientService, DoctorService doctorService, AdminService adminService, CredentialFormatValidator cfv) {
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.adminService = adminService;
		this.cfv = cfv;
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@Valid UserLoginRequest loginRequest, HttpServletRequest request, Model model) {
		
		boolean isValidUser = false;
		String type = loginRequest.getType();
		System.out.println(type);
//		if(cfv.passwordValidator(loginRequest.getPassword())!= true) {
//			model.addAttribute("error", "Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _");
//			return "forward:/login";
//		}
		
		if (type.equals("patient")) {
			isValidUser = patientService.isValidUser(loginRequest);
		}
		
		else if(type.equals("doctor")) {
			isValidUser = doctorService.isValidUser(loginRequest);
		}
		
		else if(type.equals("admin")) {
			isValidUser = adminService.isValidUser(loginRequest);
		}
		
		
		if (isValidUser) {
			HttpSession session = request.getSession(true);
			session.setAttribute("isValidUser", true);
			session.setAttribute("username", loginRequest.getUsername());
			session.setAttribute("type", type);
			return "forward:/"+type+"/welcome";
		} 
		else {
			model.addAttribute("error", "Invalid Email or Password");
			return "forward:/login";
		}
	}
	
	@GetMapping("/logout")
	public String doLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/login";
	}
}

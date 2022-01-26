package com.nrifintech.medico.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.service.DoctorService;
import com.nrifintech.medico.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
@RequestMapping("/patient/editProfile")
public class PatientController {
	private final PatientService patientService;
	private final DoctorService doctorService;
	private final AppointmentService appointmentService;

	@Autowired
	public PatientController(PatientService patientService, DoctorService doctorService,
			AppointmentService appointmentService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}
	@PostMapping("/name")
	public String updatePatientName(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		String name= (String)request.getParameter("name").toUpperCase();
		String username = (String)session.getAttribute("username");
        String msg = patientService.editName(username, name);
        if (msg == "") {
        	model.addAttribute("Message", "Name updated Successfully");
        }
        else {
        	model.addAttribute("Message", msg);
        }
        return "forward:/patient/welcome";
	}
	@PostMapping("/phone_no")
	public String updatePatientPhone(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		String number = (String)request.getParameter("phone_no");
		String username = (String)session.getAttribute("username");
        String msg = patientService.editPhone_no(username, number);
        if (msg == "") {
        	model.addAttribute("Message", "Phone Number updated Successfully");
        }
        else {
        	model.addAttribute("Message", msg);
        }
        return "forward:/patient/welcome";
	}
	
	@PostMapping("/password")
	public String updatePatientPassword(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		String curr_password = (String)request.getParameter("current_password");
		String new_password = (String)request.getParameter("new_password");
		String confirm_password = (String)request.getParameter("confirm_password");
		String username = (String)session.getAttribute("username");
        String msg = patientService.editPassword(username, curr_password, new_password, confirm_password);
        if (msg == "") {
        	model.addAttribute("Message", "Password updated Successfully");
        }
        else {
        	model.addAttribute("Message", msg);
        }
        return "forward:/patient/welcome";
	}
	
}

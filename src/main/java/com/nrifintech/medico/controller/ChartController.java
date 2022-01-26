package com.nrifintech.medico.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.entity.Appointment;

@RestController
@RequestMapping("/chart")
public class ChartController {

	
	AppointmentService apptService;
	
	@Autowired
	public ChartController(AppointmentService apptService) {
		this.apptService = apptService;
		System.out.println("Initializing chart controller");
	}
	
	@GetMapping(value="/yearappt/{doctor_id}/{year}")
	public List<Object[]> yearWiseNoOfAppointment(@PathVariable Long doctor_id, @PathVariable String year){
		System.out.println("In chart controller");
		//if(session != )
		List<Object[]> yearWiseAppt = apptService.findYearWiseSummary(doctor_id, year);
		System.out.println(yearWiseAppt);
		return yearWiseAppt;
	}
	
	@GetMapping(value = "/all")
	public List<Appointment> getAll(){
		return apptService.getAll();
	}
}

package com.nrifintech.medico.controller;

import org.springframework.ui.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.service.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	private final DoctorService doctorService;
	private final AppointmentService apptService;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentService apptService) {
		this.doctorService = doctorService;
		this.apptService = apptService;
	}
	
	@RequestMapping("/welcome")
	public String welcomeUser(HttpServletRequest request, Model model) {
		System.out.println("in doctor controller");
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		Doctor currentDoctor = doctorService.getCurrentEntity(username);
		// System.out.println(currentDoctor);
		session.setAttribute("currentDoctorName", currentDoctor.getName());
		session.setAttribute("currentDoctorID", currentDoctor.getDoctor_id());
		session.setAttribute("currentDoctor", currentDoctor);
//		System.out.println("Current doctor id is : " + currentDoctor.getDoctor_id());
		return "doctor";
	}
	
	@RequestMapping("/getappt")
	public String getAppointments(HttpServletRequest request, Model model) throws ParseException {
		HttpSession session = request.getSession();
		long doctor_id = (long)session.getAttribute("currentDoctorID");
		String myDate = (String)request.getParameter("apptdate");
		System.out.println("My date : " + myDate);
		if (myDate == "") {
			model.addAttribute("error","Date cannot be empty");
			return "doctor";
		}
		List<Appointment> appointments = apptService.findAppointmentByDate(doctor_id, myDate);
//			System.out.println("Number of appointments : " + appointments.size());
		ArrayList<ArrayList<String>> patientDetails = new ArrayList<ArrayList<String>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		for (Appointment appointment : appointments) {
			Date cDate = sdf.parse(appointment.getAppt_date().toString());
			ArrayList<String> tempList = new ArrayList<String>();
			System.out.println(cDate);
			String mins = Integer.toString(cDate.getMinutes());
			if (mins.length() < 2) mins = "0"+mins;
			String time = cDate.getHours() + ":" + mins + ":" + cDate.getSeconds() + "0";
			System.out.println(time);
			tempList.add(appointment.getPatient().getName());
			tempList.add(time);
			patientDetails.add(tempList);
		}
		System.out.println("Number of names : " + patientDetails.size());
		model.addAttribute("patientDetails", patientDetails);
		session.setAttribute("patientDetails", patientDetails);
		model.addAttribute("selectedDate", myDate);
		return "doctor";
	}
	
	@RequestMapping("/viewdoctor/{doctor_id}")
	public String viewDoctor(@PathVariable Long doctor_id, Model model) {
		Doctor currDoctor = doctorService.findById(doctor_id).get();
		Date cDate = new Date();
		Date pStarted = currDoctor.getPractice_started();
		long diffInMillies = Math.abs(cDate.getTime() - pStarted.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    model.addAttribute("yoe", diff/365);
		model.addAttribute("currDoctor", currDoctor);
		return "info";
	}
	
}

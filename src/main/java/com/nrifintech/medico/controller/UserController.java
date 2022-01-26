package com.nrifintech.medico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.response.ModifiedAppointmentResponse;
import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.service.DoctorService;
import com.nrifintech.medico.service.PatientService;

@Controller
@RequestMapping("/patient")
public class UserController {

	private final PatientService patientService;
	private final DoctorService doctorService;
	private final AppointmentService appointmentService;

	@Autowired
	public UserController(PatientService patientService, DoctorService doctorService,
			AppointmentService appointmentService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}

	@RequestMapping("/welcome")
	public String welcomeUser(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Patient currPatient = patientService.getCurrentEntity(username);
		String name = currPatient.getName();
		String fname = name.substring(0, name.indexOf(" "));
		session.setAttribute("patientName", name);
		session.setAttribute("patientFname", fname);
		session.setAttribute("currentPatientID", currPatient.getPatient_id());
		List<Doctor> allDoctors = doctorService.findAllByNameAndUpvotes();
		model.addAttribute("allDoctors", allDoctors);
		return "patient";
	}

	@PostMapping("/cancelAppt/{doctor_id}/{appt_date}/{slot}")
	public String cancelAppointment(@PathVariable Long doctor_id, @PathVariable String appt_date,
			@PathVariable String slot, HttpServletRequest request, Model model) throws ParseException {

		model.addAttribute("flag", true);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date apptDate = sdf.parse(appt_date);
		System.out.println(">>>>>>>>>>>>>> in cancelappt controller " + apptDate);
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("currentPatientID");
		System.out.println("Checker -----------> " + appt_date);
		boolean isCancelled = appointmentService.cancelAppt(id, doctor_id, apptDate, slot);
		model.addAttribute("isCancelled", isCancelled);
		return "forward:/patient/manage";
	}

	@RequestMapping("/manage")
	public String manageAppt(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		Long id = (Long) session.getAttribute("currentPatientID");
		appointmentService.updateCompleteAppts(id);
		List<Appointment> appts = appointmentService.getByPatientId(id);
		List<ModifiedAppointmentResponse> m_a = new ArrayList<ModifiedAppointmentResponse>();
		for (Appointment a : appts) {
			ModifiedAppointmentResponse m = new ModifiedAppointmentResponse(a);
			m_a.add(m);
		}
		model.addAttribute("appts", m_a);

		return "manage";
	}

	@PostMapping("/searchDoctor")
	public String searchDoctorByName(HttpServletRequest request, Model model) {
		System.out.println("Search Button Clicked");
		String searchParam = (String) request.getParameter("searchParam");
		// System.out.println(searchParam);
		String searchByOption = (String) request.getParameter("searchByOption");
		// System.out.println(searchByOption);
		String radioOption = (String) request.getParameter("radioOption");
		// System.out.println(radioOption);
		// docName = "M";
		// List<Doctor> filterDoctors = new ArrayList<Doctor>();
		List<Doctor> filterDoctors = doctorService.filterDoctor(searchParam, searchByOption, radioOption);
		model.addAttribute("allDoctors", filterDoctors);
		model.addAttribute("radioOption", radioOption);
		model.addAttribute("searchByOption", searchByOption);
		// System.out.println(filterDoctors);
		System.out.println(searchParam + " " + searchByOption + " " + radioOption);
		return "patient";
	}

	
}
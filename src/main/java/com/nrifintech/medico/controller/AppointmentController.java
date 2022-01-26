package com.nrifintech.medico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private final AppointmentService apptService;
	
	public AppointmentController(AppointmentService apptService) {
		this.apptService = apptService;
	}
	
	@GetMapping("/all")
	public List<Appointment> findAll(){
		return this.apptService.getAllAppointments();
	}
	
	@GetMapping("/btwDate")
	public List<Appointment> find(Date start, Date end){
		return this.apptService.getAllAppointments();
	}

	@GetMapping("/byDoctor/{doctor_id}/{date}")
    public ResponseEntity<List<Appointment>> getDoctorsAppointment(@PathVariable("doctor_id") String doctor_id, @PathVariable("date") String sDate) throws ParseException{
		// System.out.println(doctor_id + " " + sDate );
		// sDate= "2020-02-20";
		Long _doctor_id = Long.parseLong(doctor_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sDate);
		Date date = sdf.parse(sDate);
		List<Appointment> apptList = this.apptService.findByDoctorAndDate(_doctor_id, date);
        return new ResponseEntity<>(apptList, HttpStatus.OK);
    }

	@GetMapping("/byDoctorPatient/{doctor_id}/{patient_id}/{date}")
    public ResponseEntity<List<Appointment>> fetchApointmentsByDoctorAndPatient(@PathVariable("doctor_id") String doctor_id, @PathVariable("patient_id") String patient_id, @PathVariable("date") String sDate) throws ParseException{
		// System.out.println(doctor_id + " " + sDate );
		// sDate= "2020-02-20";
		Long _doctor_id = Long.parseLong(doctor_id);
		Long _patient_id = Long.parseLong(patient_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sDate);
		Date date = sdf.parse(sDate);
		List<Appointment> apptList = this.apptService.getAppointmentsByDoctor_idPatient_idAppointment_date(_doctor_id, _patient_id, date);
        return new ResponseEntity<>(apptList, HttpStatus.OK);
    }
	
	@PostMapping("/byDoctor/{patient_id}/{doctor_id}/{date}/{slot}")
	//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "cannot book appointment")
    public ResponseEntity<?> createAppointment(@PathVariable("patient_id") String patient_id, @PathVariable("doctor_id") String doctor_id, @PathVariable("date") String sDate, @PathVariable("slot") String slot) throws ParseException{
		System.out.println(doctor_id + " " + patient_id + " " + sDate + " " + slot);
		Long _patient_id = Long.parseLong(patient_id);
		Long _doctor_id = Long.parseLong(doctor_id);
		int _slot_no = Integer.parseInt(slot);
		System.out.println(sDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		Date date = sdf.parse(sDate);
		try {
			this.apptService.createAppointment(_doctor_id, _patient_id, date, _slot_no);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
}

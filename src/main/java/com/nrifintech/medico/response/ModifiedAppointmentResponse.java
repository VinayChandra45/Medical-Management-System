package com.nrifintech.medico.response;

import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Appointment.AppointmentId;

public class ModifiedAppointmentResponse {
	private String date;
	private Doctor doctor;
	private Patient patient;
	private String slot_no;
	private int slot;
	

	
	@Override
	public String toString() {
		return "Modified_Appointment [doctor=" + doctor + ", patient=" + patient + ", slot_no="
				+ slot_no + "]";
	}

	public ModifiedAppointmentResponse(Appointment appt) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		String strDate = dateFormat.format(appt.getAppt_date());
		this.date = strDate;
		this.doctor = appt.getDoctor();
		this.patient = appt.getPatient();
		this.slot = appt.getSlot_no();
		
		if(appt.getSlot_no() == 1)
		{
			this.slot_no = "10:00-10:15";
		}
		else if(appt.getSlot_no() == 2)
		{
			this.slot_no = "10:15-10:30";
		}
		else if(appt.getSlot_no() == 3)
		{
			this.slot_no = "10:30-10:45";
		}
		else if(appt.getSlot_no() == 4)
		{
			this.slot_no = "10:45-11:00";
		}
		else if(appt.getSlot_no() == 5)
		{
			this.slot_no = "11:00-11:15";
		}
		else if(appt.getSlot_no() == 6)
		{
			this.slot_no = "11:15-11:30";
		}
		else if(appt.getSlot_no() == 7)
		{
			this.slot_no = "11:30-11:45";
		}
		else if(appt.getSlot_no() == 8)
		{
			this.slot_no = "11:45-12:00";
		}
		else if(appt.getSlot_no() == 9)
		{
			this.slot_no = "12:00-12:15";
		}
		else if(appt.getSlot_no() == 10)
		{
			this.slot_no = "12:15-12:30";
		}
		else if(appt.getSlot_no() == 11)
		{
			this.slot_no = "12:30-12:45";
		}
		else if(appt.getSlot_no() == 12)
		{
			this.slot_no = "12:45-13:00";
		}
		else if(appt.getSlot_no() == 13)
		{
			this.slot_no = "14:00-14:15";
		}
		else if(appt.getSlot_no() == 14)
		{
			this.slot_no = "14:15-14:30";
		}
		else if(appt.getSlot_no() == 15)
		{
			this.slot_no = "14:30-14:45";
		}
		else if(appt.getSlot_no() == 16)
		{
			this.slot_no = "14:45-15:00";
		}
		else if(appt.getSlot_no() == 17)
		{
			this.slot_no = "15:00-15:15";
		}
		else if(appt.getSlot_no() == 18)
		{
			this.slot_no = "15:15-15:30";
		}
		else if(appt.getSlot_no() == 19)
		{
			this.slot_no = "15:30-15:45";
		}
		else if(appt.getSlot_no() == 20)
		{
			this.slot_no = "15:45-16:00";
		}
		else if(appt.getSlot_no() == 21)
		{
			this.slot_no = "16:00-16:15";
		}
		else if(appt.getSlot_no() == 22)
		{
			this.slot_no = "16:15-16:30";
		}
		else if(appt.getSlot_no() == 23)
		{
			this.slot_no = "16:30-16:45";
		}
		else {

			this.slot_no = "16:45-17:00";
			
		}
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getSlot_no() {
		return slot_no;
	}

	public void setSlot_no(String slot_no) {
		this.slot_no = slot_no;
	}
		
}

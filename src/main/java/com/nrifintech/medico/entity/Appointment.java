package com.nrifintech.medico.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;




@Entity
@Table(name="appointment")
public class Appointment  extends AbstractBaseEntity{
	
	@Embeddable
	public static class AppointmentId implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@ManyToOne
		@MapsId("doctor_id")
		@JoinColumn(name = "doctor_id")
		private Doctor doctor;
		
		@ManyToOne
		@MapsId("patient_id")
		@JoinColumn(name = "patient_id")
		private Patient patient;
		
		@Column(name="appt_date", columnDefinition="DATE NOT NULL")
		private Date appt_date;
		@Override
		public String toString() {
			return "Appointment [appt_date=" + appt_date + ", doctor=" + doctor + ", patient=]" + patient;
		}
	}

	public AppointmentId getAppt_id() {
		return appt_id;
	}


	public void setAppt_id(AppointmentId appt_id) {
		this.appt_id = appt_id;
	}


	@EmbeddedId
	private AppointmentId appt_id = new AppointmentId();
	
	@ManyToOne
	@MapsId("doctor_id")
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	@ManyToOne
	@MapsId("patient_id")
	@JoinColumn(name = "patient_id")
	private Patient patient;

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


	public Date getAppt_date() {
		return appt_id.appt_date;
	}



	@Override
	public String toString() {
		return "Appointment [appt_id=" + appt_id + ", doctor=" + doctor + ", patient=" + patient + ", slot_no="
				+ slot_no + "]";
	}


	public int getSlot_no() {
		return slot_no;
	}


	public void setSlot_no(int slot_no) {
		this.slot_no = slot_no;
	}


	@Column(name="slot_no")
	private int slot_no;
	
	@Column(name="status")
	private String status;

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


}


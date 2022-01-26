package com.nrifintech.medico.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nrifintech.medico.entity.Appointment;

import com.nrifintech.medico.entity.Appointment.AppointmentId;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.request.UserLoginRequest;

public interface AppointmentService {
	public List<Appointment> getByPatientId(Long patient_id);
	public boolean cancelAppt(Long patient_id, Long doctor_id, Date appt_date, String slot);
	public void updateCompleteAppts(Long patient_id);
	public boolean isValidUser(UserLoginRequest userLoginRequest);
	public List<Appointment> getAppointmentsByDoctor_idPatient_idAppointment_date(Long doctor_id, Long paitent_id, Date appt_date);
	public List<Appointment> getAllAppointments();
	// public List<Appointment> getAllAppointmentsByPatientAndDate(Patient patient);
	public List<Appointment> getAllAppointments(Date date);
	public List<Appointment> getAllAppointments(Date startDate, Date endDate);
	public List<Appointment> findByPatient(Long patient_id, java.sql.Date date);
	public List<Appointment> findByDoctorAndDate(Long doctor_id, java.util.Date date);
  public void createAppointment(Long doctor_id, Long patient_id, java.util.Date date, int slot);
  public List<Appointment> findAppointmentByDate(long doctor_id, String date);
  public List<Appointment> findAppointmentByMonth(long doctor_id, String month, String year);
	public List<Object[]> findYearWiseSummary(long doctor_id, String year);
	public List<Appointment> getAll();
	public List<Appointment> rangeAppointmentByDoctor(Long doctor_id, String date1, String date2);

}

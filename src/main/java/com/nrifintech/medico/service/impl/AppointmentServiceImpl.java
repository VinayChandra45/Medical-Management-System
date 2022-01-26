package com.nrifintech.medico.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.nrifintech.medico.entity.Admin;
import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Appointment.AppointmentId;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.repository.AppointmentRepository;
import com.nrifintech.medico.repository.DoctorRepository;
import com.nrifintech.medico.repository.IAbstractBaseRepository;
import com.nrifintech.medico.request.UserLoginRequest;
import com.nrifintech.medico.service.AppointmentService;
import com.nrifintech.medico.utility.TimeDifferenceChecker;
import com.nrifintech.medico.service.IAbstractBaseService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository apptRepository;
	TimeDifferenceChecker tdc;

	@Autowired
	public AppointmentServiceImpl(AppointmentRepository apptRepository, TimeDifferenceChecker tdc) {

		this.apptRepository = apptRepository;
		this.tdc = tdc;
	}
  
	@Override
	public List<Appointment> getByPatientId(Long patient_id) {
		List<Appointment> appts = apptRepository.findByPatient(patient_id);
		return appts;
	}

	@Override
	public boolean isValidUser(UserLoginRequest userLoginRequest) {
		// TODO Auto-generated method stub
		if (Objects.isNull(userLoginRequest.getUsername()) || Objects.isNull(userLoginRequest.getPassword())) {
			return false;
		}
		return false;
	}

	@Override
	public List<Appointment> getAllAppointments(Date date) {
		return this.apptRepository.findAllByAppt_date(date);
	}

	@Override
	public List<Appointment> getAllAppointments(Date startDate, Date endDate) {
		return this.apptRepository.findAppointmentsBetweenDate(startDate, endDate);
	}

	@Override
	public boolean cancelAppt(Long patient_id, Long doctor_id, Date appt_date, String slot) {
		System.out.println(">>>>>>>>>> in apptserviceimpl " + appt_date);
		// boolean flag = tdc.check(appt_date,slot);
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss", Locale.ENGLISH);
		Date currDate = new Date();
		long diffInMillies = Math.abs(currDate.getTime() - appt_date.getTime());
		// System.out.println(">>>>>>>>. in cancelappt service " + );
		if (diffInMillies / 1000 <= 24 * 60 * 60)
			flag = false;
		if (flag)
			apptRepository.cancelAppointment(patient_id, doctor_id, appt_date);
		return flag;
	}

	@Override
	public void updateCompleteAppts(Long patient_id) {
		apptRepository.completeAppointment(patient_id);
	}


	@Override
	public List<Appointment> getAllAppointments() {
		return this.apptRepository.getAllAppointments();
	}

	@Override
	public List<Appointment> findByPatient(Long patient_id, java.sql.Date date) {
		return this.apptRepository.findByPatient_id(patient_id, date);
	}

	@Override
	public List<Appointment> findByDoctorAndDate(Long doctor_id, java.util.Date date) {
		return this.apptRepository.findByDoctor_idAndDate(doctor_id, date);
	}

	@Override
	public void createAppointment(Long doctor_id, Long patient_id, java.util.Date date, int slot) {
		this.apptRepository.createAppointment(doctor_id, patient_id, date, slot);
	}
	
	@Override
	public List<Appointment> findAppointmentByDate(long doctor_id, String date) {
		//Pageable firstPageWithTwoElements = PageRequest.of(0, 200);	
		List<Appointment> appointments = apptRepository.findAppointmentsByDoctorIDByDate(doctor_id, date); //, firstPageWithTwoElements);
		return appointments;
	}
	
	@Override
	public List<Appointment> findAppointmentByMonth(long doctor_id, String month, String year) {
		// Pageable firstPageWithTwoElements = PageRequest.of(0, 200);	
		List<Appointment> appointments = apptRepository.findAppointmentsByDoctorIDByMonth(doctor_id, month, year); //, firstPageWithTwoElements);
		return appointments;
	}
	
	@Override
	public List<Object[]> findYearWiseSummary(long doctor_id, String year) {
		System.out.println("Inside appt service impls");
		List<Object[]> yearWiseAppt = apptRepository.findAppointmentsByDoctorIDYearWise(year, doctor_id);
		return yearWiseAppt;
	}
	
	@Override
	public List<Appointment> getAll() {
		return apptRepository.findAll();
	}
	
	@Override
	public List<Appointment> rangeAppointmentByDoctor(Long doctor_id, String date1, String date2) {
		return apptRepository.findAppointmentByDoctorIdInRangeDate(doctor_id, date1, date2);
	}

	@Override
	public List<Appointment> getAppointmentsByDoctor_idPatient_idAppointment_date(Long doctor_id, Long patient_id,
			Date appt_date) {
				return this.apptRepository.findByDoctorPatientAppt_date(doctor_id, patient_id, appt_date);
	}

}

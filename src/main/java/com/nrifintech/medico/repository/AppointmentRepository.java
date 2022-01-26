package com.nrifintech.medico.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nrifintech.medico.entity.Appointment;
import com.nrifintech.medico.entity.Appointment.AppointmentId;
import com.nrifintech.medico.entity.Doctor;
import com.nrifintech.medico.entity.Patient;
import com.nrifintech.medico.response.GraphResponse;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, AppointmentId> {

	@Query(value = "SELECT * FROM appointment WHERE patient_id= ? AND status = 'active' ORDER BY appt_date", nativeQuery = true)
	List<Appointment> findByPatient(Long patient_id);

	@Modifying
	@Query(value = "UPDATE appointment SET status = 'cancel' WHERE patient_id= ? AND doctor_id = ? AND appt_date = ?", nativeQuery = true)
	void cancelAppointment(Long patient_id, Long doctor_id, Date appt_date);

	@Modifying
	@Query(value = "UPDATE appointment SET status = 'complete' WHERE patient_id = ? AND appt_date < CURDATE()", nativeQuery = true)
	void completeAppointment(Long patient_id);

	@Query(value = "select * from appointment where doctor_id=:doc and patient_id=:pat and Date(appt_date)=:date and status='active' ", nativeQuery = true)
	public List<Appointment> findByDoctorPatientAppt_date(Long doc, Long pat, Date date);

	@Query(value = "select * from appointment where appt_date=:date", nativeQuery = true)
	public List<Appointment> findAllByAppt_date(Date date);

	@Query(value = "select * from appointment where appt_date between :startDate and :endDate", nativeQuery = true)
	public List<Appointment> findAppointmentsBetweenDate(Date startDate, Date endDate);

	@Query(value = "select * from appointment", nativeQuery = true)
	public List<Appointment> getAllAppointments();

	public List<Appointment> findByPatient(Patient patient);

	public List<Appointment> findByDoctor(Doctor doctor);

	@Query(value = "select * from appointment where  patient_id=:patient_id and Date(appt_date)=:date", nativeQuery = true)
	public List<Appointment> findByPatient_id(Long patient_id, java.sql.Date date);

	@Query(value = "select * from appointment where  doctor_id=:doctor_id and date(appt_date)=:date and status = 'active'", nativeQuery = true)
	public List<Appointment> findByDoctor_idAndDate(Long doctor_id, Date date);

	@Modifying
	@Query(value = "Insert into appointment(doctor_id, patient_id, appt_date, created_at, updated_at, slot_no, status) values(:doctor_id, :patient_id, :date, now(), now(), :slot, 'active')", nativeQuery = true)
	public void createAppointment(Long doctor_id, Long patient_id, Date date, int slot);

	@Query(value = "select * from appointment where doctor_id=? and date(appt_date)=? order by appt_date", nativeQuery = true)
	List<Appointment> findAppointmentsByDoctorIDByDate(long doctor_id, String date); // ,Pageable pageable);

	@Query(value = "select * from appointment where doctor_id=? and month(appt_date)=? and year(appt_date)=? order by appt_date", nativeQuery = true)
	List<Appointment> findAppointmentsByDoctorIDByMonth(long doctor_id, String month, String year); // ,Pageable
																									// pageable);

	@Query(value = "Select  MONTH(appt_date), count(*) from appointment where YEAR(appt_date)=? and doctor_id=? group by MONTH(appt_date) order by 1", nativeQuery = true)
	List<Object[]> findAppointmentsByDoctorIDYearWise(String year, long doctor_id);

	@Query(value = "select * from appointment where doctor_id = ? and date(appt_date) >= ? and date(appt_date) <= ? order by appt_date;", nativeQuery = true)
	List<Appointment> findAppointmentByDoctorIdInRangeDate(Long doctor_id, String date1, String date2);

	@Query(value = "SELECT  MONTHNAME(CONCAT('2018-',res.label,'-1')) as label, res.y as y from (SELECT MONTH(t.appt_date) as label, COUNT(MONTH(t.appt_date)) as y FROM (select appt_date from appointment  ) as t WHERE year(t.appt_date)=? GROUP BY MONTH(t.appt_date)) as res ORDER BY (res.label)", nativeQuery = true)
	List<GraphResponse> countAppointmentsByMonth(String year);

}

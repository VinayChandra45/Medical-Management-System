package com.nrifintech.medico.request;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DoctorRegisterRequest {
	private String name;
	private String username;
	private int fees;
	private String password;
	private String specialization;
	private Date practice_started;
	private String degree;
	private String descr;
	public DoctorRegisterRequest() {
	}
	public DoctorRegisterRequest(String name, String username, int fees, String password, String specialization,
			Date practice_started, String degree, String descr) {
		this.name = name;
		this.username = username;
		this.fees = fees;
		this.password = password;
		this.specialization = specialization;
		this.practice_started = practice_started;
		this.degree = degree;
		this.descr = descr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public Date getPractice_started() {
		return practice_started;
	}
	public void setPractice_started(Date practice_started) {
		this.practice_started = practice_started;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	@Override
	public String toString() {
		return "EditRequest [name=" + name + ", username=" + username + ", fees=" + fees + ", password=" + password
				+ ", specialization=" + specialization + ", practice_started=" + practice_started + ", degree=" + degree
				+ ", descr=" + descr + "]";
	}
	
	
}

package com.nrifintech.medico.entity;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// doctor table

@Entity
@Table(name="doctor")
public class Doctor extends AbstractBaseEntity {
	private static final long serialVersionUID = 6715094082832854125L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctor_id;
	
	@OneToMany(mappedBy="doctor")
	Collection<Appointment> appointments;
	
	@Column(name="email", unique=true)
	private String username; //email
	
	@Column(name="fees")
	private int fees;
	
	@Column(name="upvotes")
	private int upvotes;
	
	@Column(name="specialization")
	private String specialization;
	
	@Column(name="descr")
	private String descr;
	
	@Column(name="profile_pic")
	private String profile_pic;
	
	@Column(name="name")
	private String name;
	
	@Column(name="degree")
	private String degree;
	
	@Column(name="status")
	private boolean status;
	
	public Long getDoctor_id() {
		return doctor_id;
	}

	@Column(name="practice_started")
	private Date practice_started;
	
	@Column(name="password")
	private String password;

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

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getPractice_started() {
		return practice_started;
	}

	public void setPractice_started(Date practice_started) {
		this.practice_started = practice_started;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@Override
//	public String toString() {
//		return "Doctor [doctor_id=" + doctor_id + ", appointments=" + appointments + ", username=" + username
//				+ ", fees=" + fees + ", upvotes=" + upvotes + ", specialization=" + specialization + ", descr=" + descr
//				+ ", profile_pic=" + profile_pic + ", name=" + name + ", degree=" + degree + ", status=" + status
//				+ ", practice_started=" + practice_started + ", password=" + password + "]";
//	}
	
	

}

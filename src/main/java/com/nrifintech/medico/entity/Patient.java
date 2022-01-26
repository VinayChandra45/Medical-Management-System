package com.nrifintech.medico.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// patient table

@Entity
@Table(name="patient")
public class Patient extends AbstractBaseEntity {
	private static final long serialVersionUID = 6715094082833854125L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patient_id;
	
	@OneToMany(mappedBy="patient")
	Collection<Appointment> appointments;
	
	@Column(name="email", unique=true)
	private String username; //email
	
	@Column(name="name")
	private String name;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="phone_no")
	private String phone_no;

	
	public Long getPatient_id() {
		return patient_id;
	}

	public String getName() {
		return name;
	}
	
	public String getusername() {
		return username;
	}
	
	public String getPhoneNumber() {
		return phone_no;
	}

	public void setName(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setusername(String username) {
		this.username = username;
	}
	
	public void setPhoneNumber(String phone_no) {
		this.phone_no = phone_no;
	}
	
//	@Override
//	public String toString() {
//		return "Name : "+name+" Email : " + username + " Password : " + password + " Phone no : " + phone_no;
//	}
}

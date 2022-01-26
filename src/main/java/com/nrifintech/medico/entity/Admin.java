package com.nrifintech.medico.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// admin table

@Entity
@Table(name="admin")
public class Admin extends AbstractBaseEntity{
	private static final long serialVersionUID = 6715094081832854125L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;
	
	@Column(name="email", unique=true)
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;

	public Long getAdmin_id() {
		return admin_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
